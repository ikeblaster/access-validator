package cz.zcu.kiv.accessvalidator.components.validator;

import cz.zcu.kiv.accessvalidator.common.Dialogs;
import cz.zcu.kiv.accessvalidator.common.FileChooserEx;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.FileTreeObject;
import cz.zcu.kiv.accessvalidator.components.validator.treeobjects.TreeObject;
import cz.zcu.kiv.accessvalidator.validator.AccdbSimilarityChecker;
import cz.zcu.kiv.accessvalidator.validator.AccdbValidator;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class ValidatorController {

    @FXML
    private TreeView<TreeObject> treeView;
    private TreeItemRoot dbFiles;

    private FileChooserEx fileChooser = new FileChooserEx(this.getClass().getSimpleName());


    //region================== GUI initialization ==================

    @FXML
    public void initialize() {
        // tooltips support
        this.treeView.setCellFactory(tv -> {
            final Tooltip tooltip = new Tooltip();

            TreeCell<TreeObject> cell = new TreeCell<TreeObject>() {
                String styleClass = null;

                @Override
                public void updateItem(TreeObject item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        this.setText(null);
                        this.setGraphic(null);
                        this.getStyleClass().remove(this.styleClass);
                        this.styleClass = null;
                    } else {
                        this.setText(item.toString());

                        this.getStyleClass().remove(this.styleClass);
                        this.styleClass = item.getStyleclass();
                        this.getStyleClass().add(this.styleClass);
                        //this.setGraphic(new ImageView(this.getClass().getResource("/icons/if_sign-check_299110.png").toExternalForm()));
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
                this.dbFiles.addFile(file);
            }
        }

        this.dbFiles.getChildren().sort(Comparator.comparing(javafx.scene.control.TreeItem::toString));
    }


    public void actionRemoveDB() {
        this.dbFiles.getChildren().removeAll(this.treeView.getSelectionModel().getSelectedItems());
    }


    public void actionTestDBs(Rule rule) {
        Map<File, List<SimilarFiles>> duplicates = this.findDuplicates(this.dbFiles.getFiles());

        for (FileTreeObject file : this.dbFiles.getFileWrappers()) {
            try {

                AccdbValidator validator = new AccdbValidator(file.getFile());
                file.setValid(validator.validate(rule));

                file.setSimilarFiles(duplicates.getOrDefault(file.getFile(), Collections.emptyList()));

            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.showErrorBox("Kontrolu se nepodařilo zahájit", e.getLocalizedMessage());
            }
        }

        this.treeView.refresh();
    }

    //endregion



    private Map<File, List<SimilarFiles>> findDuplicates(List<File> files) {

        try {

            AccdbSimilarityChecker simchecker = new AccdbSimilarityChecker(files);
            return simchecker.getSimilarFiles();

        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorBox("Kontrolu plagiátů/duplikátů se nepodařilo zahájit", e.getLocalizedMessage());
        }

        return Collections.emptyMap();
    }

}