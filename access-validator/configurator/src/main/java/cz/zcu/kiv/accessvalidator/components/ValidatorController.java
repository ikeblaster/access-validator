package cz.zcu.kiv.accessvalidator.components;

import cz.zcu.kiv.accessvalidator.common.Dialogs;
import cz.zcu.kiv.accessvalidator.common.FileChooserEx;
import cz.zcu.kiv.accessvalidator.adaptors.ListViewFileAdaptor;
import cz.zcu.kiv.accessvalidator.validator.AccdbValidator;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ValidatorController {

    @FXML
    private ListView<ListViewFileAdaptor> dbFiles;
    private ObservableList<ListViewFileAdaptor> files = FXCollections.observableArrayList();

    private FileChooserEx fileChooser = new FileChooserEx(this.getClass().getSimpleName());



    //region================== GUI initialization ==================

    public void onLoad(Stage stage) {
        this.fileChooser.setStage(stage);
    }

    @FXML
    public void initialize() {
        this.fileChooser.addExtensionFilter(new FileChooser.ExtensionFilter("Access databáze", "*.accdb", "*.mdb"));
        this.dbFiles.setItems(this.files);
    }

    //endregion


    //region================== Actions (buttons) ==================

    public void actionAddDB() {
        List<File> files = this.fileChooser.openMultipleFiles();
        List<File> existing = this.files.stream().map(ListViewFileAdaptor::getFile).collect(Collectors.toList());

        for (File file : files) {
            if(!existing.contains(file)) {
                this.files.add(new ListViewFileAdaptor(file));
            }
        }

        FXCollections.sort(this.files, Comparator.comparing(ListViewFileAdaptor::toString));
    }


    public void actionRemoveDB() {
        this.files.removeAll(this.dbFiles.getSelectionModel().getSelectedItems());
    }

    public void actionTestDBs(Rule rule) {
        for (ListViewFileAdaptor file : this.dbFiles.getItems()) {
            try {
                AccdbValidator validator = new AccdbValidator(file.getFile());
                file.setValid(validator.validate(rule));

            } catch (IOException e) {
                Dialogs.showErrorBox("Kontrolu se nepodařilo zahájit", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        this.dbFiles.refresh();
    }

    //endregion




}