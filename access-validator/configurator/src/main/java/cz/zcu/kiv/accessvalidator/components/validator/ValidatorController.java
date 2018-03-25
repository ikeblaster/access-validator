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


public class ValidatorController {

    @FXML
    private TreeView<TreeObject> treeView;
    private TreeItemRoot dbFiles;

    private boolean checkSimilarities = true;
    private Set<SimilarityElement> ignoredSimilarities = new HashSet<>();

    private FileChooserEx fileChooser = new FileChooserEx(this.getClass().getSimpleName());
    private Rule lastCheckedRule;


    //region================== GUI initialization ==================

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

    public void onLoad(Stage stage) {
        this.fileChooser.setStage(stage);
    }

    //endregion


    //region================== Actions (buttons) ==================

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


    public void actionRemoveDB() {
        this.dbFiles.getChildren().removeAll(this.treeView.getSelectionModel().getSelectedItems());
    }


    public void actionTestDBs(Rule rule) {
        this.lastCheckedRule = rule;

        if(rule == null) {
            return;
        }

        Map<File, SimilarFiles> similarFiles = this.findSimilarFiles(this.dbFiles.getFiles());

        for (FileTreeObject treeFile : this.dbFiles.getFileWrappers()) {
            try {

                AccdbValidator validator = new AccdbValidator(treeFile.getFile(), rule);

                treeFile.clearInfo();
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

    public void actionHideSimilarity(SimilarityElement similarityElement) {
        this.ignoredSimilarities.add(similarityElement);
        this.actionTestDBs(this.lastCheckedRule);
    }

    public void actionResetHiddenSimilarities() {
        this.ignoredSimilarities.clear();
        this.actionTestDBs(this.lastCheckedRule);
    }

    public void actionSetCheckSimilarities(boolean checkSimilarities) {
        this.checkSimilarities = checkSimilarities;
        this.actionTestDBs(this.lastCheckedRule);
    }

    //endregion



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