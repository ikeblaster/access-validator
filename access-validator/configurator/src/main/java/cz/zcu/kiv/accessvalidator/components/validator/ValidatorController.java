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
import javafx.scene.input.TransferMode;
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

        // add drag and drop support
        this.treeView.setOnDragOver(event -> {
            if(event.getDragboard().hasFiles()) {

                for (File file : event.getDragboard().getFiles()) {
                    if(this.checkFileSupported(file)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        break;
                    }
                }

            }
            event.consume();
        });

        this.treeView.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {

                this.actionAddDB(event.getDragboard().getFiles());
                event.setDropCompleted(true);

            }
            event.consume();
        });

    }

    //endregion


    //region================== Actions (buttons) ==================

    /**
     * Action for adding database files into the tree.
     * Opens file chooser.
     */
    public void actionAddDB() {
        this.actionAddDB(this.fileChooser.openMultipleFiles());
    }

    /**
     * Action for adding database files into the tree.
     * @param files List of files to be added.
     */
    public void actionAddDB(List<File> files) {
        List<File> existing = this.dbFiles.getFiles();

        for (File file : files) {
            if (this.checkFileSupported(file) && !existing.contains(file)) {
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

    /**
     * Checks whether a file is supported by validator. Check is extension based only.
     *
     * @param file File to be checked.
     * @return {@code true} when file is supported, {@code false} otherwise.
     */
    private boolean checkFileSupported(File file) {
        String name = file.getName();
        try {
            String ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();

            if(ext.equals("accdb") || ext.equals("mdb")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}