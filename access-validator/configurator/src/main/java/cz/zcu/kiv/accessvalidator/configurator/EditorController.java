package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.components.ActiveRulesController;
import cz.zcu.kiv.accessvalidator.components.BatchController;
import cz.zcu.kiv.accessvalidator.components.DetailsController;
import cz.zcu.kiv.accessvalidator.components.LibraryController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class EditorController {

    @FXML
    public LibraryController libraryController;

    @FXML
    public ActiveRulesController activeRulesController;

    @FXML
    public DetailsController detailsController;

    @FXML
    public BatchController batchController;



    //region================== GUI initialization ==================

    public void onLoad(Stage stage) {
        this.libraryController.onLoad(stage, this.activeRulesController);
        this.activeRulesController.onLoad(stage, this.detailsController);
        this.detailsController.onLoad(stage);
        this.batchController.onLoad(stage);
    }

    @FXML
    public void initialize() {
    }

    //endregion


    //region================== Actions (buttons) ==================


    @FXML
    public void actionExitApp() {
        Platform.exit();
    }


    @FXML
    public void actionNewFile() {
        this.activeRulesController.actionNewFile();
    }
    @FXML
    public void actionOpenFile() {
        this.activeRulesController.actionOpenFile();
    }
    @FXML
    public void actionSaveFile() {
        this.activeRulesController.actionSaveFile();
    }
    @FXML
    public void actionSaveAs() {
        this.activeRulesController.actionSaveAs();
    }
    @FXML
    public void actionDeleteRule() {
        this.activeRulesController.actionDeleteRule();
    }


    @FXML
    public void actionAddDB() {
        this.batchController.actionAddDB();
    }
    @FXML
    public void actionRemoveDB() {
        this.batchController.actionRemoveDB();
    }
    @FXML
    public void actionTestDBs() {
        this.batchController.actionTestDBs(this.activeRulesController.getRootRule());
    }


    //endregion





}