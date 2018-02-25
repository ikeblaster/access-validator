package cz.zcu.kiv.accessvalidator.batch;

import cz.zcu.kiv.accessvalidator.common.Dialogs;
import cz.zcu.kiv.accessvalidator.common.FileChooserEx;
import cz.zcu.kiv.accessvalidator.common.ListViewFileAdaptor;
import cz.zcu.kiv.accessvalidator.validator.AccdbValidator;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.application.Platform;
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


public class BatchController {

    @FXML
    private ListView<ListViewFileAdaptor> filesList;
    private ObservableList<ListViewFileAdaptor> files = FXCollections.observableArrayList();

    private Rule rule;

    private FileChooserEx fileChooser = new FileChooserEx();


    //region================== GUI initialization ==================

    public void onLoad(Stage stage, Rule rule) {
        this.rule = rule;
        this.fileChooser.setStage(stage);
    }

    @FXML
    public void initialize() {
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Access databáze", "*.accdb", "*.mdb"));

        this.initializeFilesList();
    }

    private void initializeFilesList() {
        this.filesList.setItems(this.files);

        //this.filesList.setCellFactory(param -> {
        //    return new
        //});
    }


    //endregion


    //region================== Actions (buttons) ==================


    @FXML
    public void actionExitApp() {
        Platform.exit();
    }

    @FXML
    void actionAddFile() {
        List<File> files = this.fileChooser.openMultipleFiles();
        List<File> existing = this.files.stream().map(ListViewFileAdaptor::getFile).collect(Collectors.toList());

        for (File file : files) {
            if(!existing.contains(file)) {
                this.files.add(new ListViewFileAdaptor(file));
            }
        }

        FXCollections.sort(this.files, Comparator.comparing(ListViewFileAdaptor::toString));
    }


    @FXML
    void actionRemoveFile() {
        this.files.removeAll(this.filesList.getSelectionModel().getSelectedItems());
    }

    @FXML
    void actionValidate() {
        for (ListViewFileAdaptor file : this.filesList.getItems()) {
            try {
                AccdbValidator validator = new AccdbValidator(file.getFile());
                file.setValid(validator.validate(this.rule));

            } catch (IOException e) {
                Dialogs.showErrorBox("Kontrolu se nepodařilo zahájit", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        this.filesList.refresh();
    }


    //endregion




}