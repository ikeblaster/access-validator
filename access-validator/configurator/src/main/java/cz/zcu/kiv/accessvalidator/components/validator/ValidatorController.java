package cz.zcu.kiv.accessvalidator.components.validator;

import cz.zcu.kiv.accessvalidator.common.Dialogs;
import cz.zcu.kiv.accessvalidator.common.FileChooserEx;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.FileTreeObject;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.TreeObject;
import cz.zcu.kiv.accessvalidator.validator.AccdbSimilarityChecker;
import cz.zcu.kiv.accessvalidator.validator.AccdbValidator;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Controller for validator pane in JavaFX UI.
 * Contains a treeview with files and nested details (failed
 * rules and/or similar files together with their similarities).
 *
 * @author ike
 */
public class ValidatorController {

    /**
     * Tree with files and infomations.
     */
    @FXML
    private TreeView<TreeObject> treeView;

    /**
     * Root item containing all added database files.
     */
    private TreeItemRoot dbFiles;

    /**
     * Sets whether similarities should be checked on after every validity check.
     */
    private boolean checkSimilarities = true;

    /**
     * Set of ignored similarities.
     */
    private Set<SimilarityElement> ignoredSimilarities = new HashSet<>();

    /**
     * File chooser for adding databases.
     */
    private FileChooserEx fileChooser = new FileChooserEx(this.getClass().getSimpleName());

    /**
     * Last used rule for checking validity. Used for rechecks after modifying options of pane.
     */
    private Rule lastCheckedRule;


    //region================== GUI initialization ==================

    /**
     * Called after GUI load. Sets parent stage.
     *
     * @param stage Parent stage for pane.
     */
    public void onLoad(Stage stage) {
        this.fileChooser.setStage(stage);
    }

    /**
     * Called during GUI initialization.
     * Initializes elements inside pane (adds cell factory for cells,
     * sets tree root, adds extension filter to file chooser.).
     */
    @FXML
    public void initialize() {
        // tooltips support
        this.treeView.setCellFactory(tv -> {
            final Tooltip tooltip = new Tooltip();

            TreeCell<TreeObject> cell = new TreeCell<TreeObject>() {
                Collection<String> styleClass = Collections.emptyList();

                @Override
                public void updateItem(TreeObject item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        this.setText(null);
                        this.getStyleClass().removeAll(this.styleClass);
                        this.styleClass = Collections.emptyList();
                    } else {
                        this.setText(item.toString());
                        this.getStyleClass().removeAll(this.styleClass);
                        this.styleClass = item.getStyleClasses();
                        this.getStyleClass().addAll(this.styleClass);
                        this.setContextMenu(item.getContextMenu());
                    }
                }
            };

            cell.setOnMouseEntered(event -> {
                TreeItem<TreeObject> treeItem = cell.getTreeItem();
                if(treeItem == null) {
                    return;
                }
                TreeObject obj = treeItem.getValue();

                if (obj.hasTooltipText()) {
                    tooltip.setText(obj.getTooltipText());
                    cell.setTooltip(tooltip);
                } else {
                    cell.setTooltip(null);
                }
            });
            return cell;
        });

        // add root
        this.dbFiles = new TreeItemRoot();
        this.treeView.setShowRoot(false);
        this.treeView.setRoot(this.dbFiles);
        this.treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        this.fileChooser.addExtensionFilter(new FileChooser.ExtensionFilter("Access databáze", "*.accdb", "*.mdb"));
    }

    //endregion


    //region================== Actions (buttons) ==================

    /**
     * Action for adding database files into the tree.
     */
    public void actionAddDB() {
        List<File> files = this.fileChooser.openMultipleFiles();
        List<File> existing = this.dbFiles.getFiles();

        for (File file : files) {
            if (!existing.contains(file)) {
                FileTreeObject treeFile = this.dbFiles.addFile(file);
                treeFile.onHideSimilarity(this::actionHideSimilarity);
            }
        }

        this.dbFiles.getChildren().sort(Comparator.comparing(javafx.scene.control.TreeItem::toString, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Action from removing database file from the tree.
     */
    public void actionRemoveDB() {
        this.dbFiles.getChildren().removeAll(this.treeView.getSelectionModel().getSelectedItems());
    }

    /**
     * Action for testing all added databases against the rule. Also checks for similarities.
     * @param rule
     */
    public void actionTestDBs(Rule rule) {
        this.lastCheckedRule = rule;

        if(rule == null) {
            return;
        }

        Map<File, SimilarFiles> similarFiles = this.findSimilarFiles(this.dbFiles.getFiles());

        for (FileTreeObject treeFile : this.dbFiles.getFileWrappers()) {
            try {

                AccdbValidator validator = new AccdbValidator(treeFile.getFile(), rule);

                treeFile.resetState();
                treeFile.setValid(validator.validate());
                treeFile.setFailedRules(validator.getFailedRules());
                treeFile.setSimilarFiles(similarFiles.get(treeFile.getFile()));

            } catch (Exception e) {
                e.printStackTrace();
                Dialogs.showErrorBox("Kontrola souboru '" + treeFile.getFile() + "' se nezdařila", e.getLocalizedMessage());
            }
        }

        this.treeView.refresh();
    }

    /**
     * Action for hiding similarity between files.
     *
     * @param similarityElement Similarity to be hidden.
     */
    public void actionHideSimilarity(SimilarityElement similarityElement) {
        this.ignoredSimilarities.add(similarityElement);
        this.actionTestDBs(this.lastCheckedRule);
    }

    /**
     * Action for resetting hidden similarities.
     * Recheckes all databases afterwards.
     */
    public void actionResetHiddenSimilarities() {
        this.ignoredSimilarities.clear();
        this.actionTestDBs(this.lastCheckedRule);
    }

    /**
     * Action for enabling or disabling similarity checking.
     * Recheckes all databases afterwards.
     */
    public void actionSetCheckSimilarities(boolean checkSimilarities) {
        this.checkSimilarities = checkSimilarities;
        this.actionTestDBs(this.lastCheckedRule);
    }

    //endregion


    /**
     * Gets a map with files and similar files to them.
     *
     * @param files Collection of databases for similarity check.
     * @return Map with files and similar files to them.
     */
    private Map<File, SimilarFiles> findSimilarFiles(List<File> files) {
        if(this.checkSimilarities) {
            try {
                AccdbSimilarityChecker checker = new AccdbSimilarityChecker(files);
                checker.setIgnoreSimilarities(this.ignoredSimilarities);
                return checker.getSimilarFiles();
            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.showErrorBox("Kontrolu plagiátů/duplikátů se nepodařilo zahájit", e.getLocalizedMessage());
            }
        }

        return Collections.emptyMap();
    }

}