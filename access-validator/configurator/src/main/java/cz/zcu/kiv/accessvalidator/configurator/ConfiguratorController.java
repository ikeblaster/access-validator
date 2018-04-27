package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.components.activerules.ActiveRulesController;
import cz.zcu.kiv.accessvalidator.components.details.DetailsController;
import cz.zcu.kiv.accessvalidator.components.library.LibraryController;
import cz.zcu.kiv.accessvalidator.components.validator.ValidatorController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.stage.Stage;

/**
 * Controller for main window.
 * Contains a menu, toolbar and four panes.
 *
 * @author ike
 */
public class ConfiguratorController {

    /**
     * Pane with list of accessible rules.
     */
    @FXML
    public LibraryController libraryController;

    /**
     * Pane with tree of active rules.
     */
    @FXML
    public ActiveRulesController activeRulesController;

    /**
     * Pane with details for highlighted rule (its properties).
     */
    @FXML
    public DetailsController detailsController;

    /**
     * Pane with tree of databases for validating.
     */
    @FXML
    public ValidatorController validatorController;


    //region================== GUI initialization ==================

    /**
     * Called after GUI load. Sets parent stage.
     *
     * @param stage Parent stage for pane.
     */
    public void onLoad(Stage stage) {
        this.libraryController.onLoad(stage, this.activeRulesController);
        this.activeRulesController.onLoad(stage, this.detailsController);
        this.detailsController.onLoad(stage);
        this.validatorController.onLoad(stage);
    }

    /**
     * Called during GUI initialization.
     */
    @FXML
    public void initialize() {
    }

    //endregion


    //region================== Actions (buttons) ==================

    /**
     * Exits the app.
     */
    @FXML
    public void actionExitApp() {
        Platform.exit();
    }


    /**
     * Action for adding new rule amongst active rules.
     */
    @FXML
    public void actionAddRule() {
        this.libraryController.actionAddSelectedRule();
    }


    /**
     * Action for creating a new set of rules (new file).
     */
    @FXML
    public void actionNewFile() {
        this.activeRulesController.actionNewFile();
    }

    /**
     * Action for opening an existing file with rules.
     */
    @FXML
    public void actionOpenFile() {
        this.activeRulesController.actionOpenFile();
    }

    /**
     * Action for saving current active rules into file.
     */
    @FXML
    public void actionSaveFile() {
        this.activeRulesController.actionSaveFile();
    }

    /**
     * Action for saving current active rules into new file.
     */
    @FXML
    public void actionSaveAs() {
        this.activeRulesController.actionSaveAs();
    }

    /**
     * Action for deleting highlighted active rule.
     */
    @FXML
    public void actionDeleteRule() {
        this.activeRulesController.actionDeleteRule();
    }


    /**
     * Action for adding new database for checking.
     */
    @FXML
    public void actionAddDB() {
        this.validatorController.actionAddDB();
    }

    /**
     * Action for removing a database from checking.
     */
    @FXML
    public void actionRemoveDB() {
        this.validatorController.actionRemoveDB();
    }

    /**
     * Action for checking all added databases.
     */
    @FXML
    public void actionTestDBs() {
        this.validatorController.actionTestDBs(this.activeRulesController.getRootRule());
    }

    /**
     * Action for resetting hidden similarities between database files.
     */
    @FXML
    public void actionResetHiddenSimilarities() {
        this.validatorController.actionResetHiddenSimilarities();
    }

    /**
     * Action for toggling similarity checking.
     *
     * @param event Source event - menu item with checkbox.
     */
    @FXML
    public void actionToggleCheckSimilarities(ActionEvent event) {
        CheckMenuItem menuItem = (CheckMenuItem) event.getSource();
        this.validatorController.actionSetCheckSimilarities(menuItem.isSelected());
    }


    //endregion





}