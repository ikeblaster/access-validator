package cz.zcu.kiv.accessvalidator.configurator.components.library;

import cz.zcu.kiv.accessvalidator.configurator.components.activerules.ActiveRulesController;
import cz.zcu.kiv.accessvalidator.validator.RulesRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * Controller for Library pane in JavaFX UI.
 * Contains a listview of all accessible rules.
 *
 * @author Vojtech Kinkor
 */
public class LibraryController {

    /**
     * Listview with all accessible rules.
     */
    @FXML
    public ListView<ListViewRuleAdaptor> rules;

    /**
     * Reference to a controller of pane with a set of active rules.
     */
    private ActiveRulesController activeRulesController;


    //region================== GUI initialization ==================

    /**
     * Called after GUI load. Sets parent stage and dependency to other parts of GUI.
     *
     * @param stage Parent stage for pane.
     * @param activeRulesController Controller of the pane with a set of active rules.
     */
    public void onLoad(Stage stage, ActiveRulesController activeRulesController) {
        this.activeRulesController = activeRulesController;
    }

    /**
     * Called during GUI initialization.
     * Initializes elements inside pane (adds rules into the list and sets doubleclick event).
     */
    @FXML
    public void initialize() {
        this.rules.getItems().addAll(ListViewRuleAdaptor.wrapAll(RulesRepository.getAll()));
        //FXCollections.sort(this.rules.getItems(), Comparator.comparing(Rule::toString));

        this.rules.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                this.actionAddSelectedRule();
            }
        });
    }

    //endregion


    //region================== Actions (buttons) ==================

    /**
     * Adds highlighted rule to the set of active rules.
     */
    public void actionAddSelectedRule() {
        ListViewRuleAdaptor origin = this.rules.getSelectionModel().getSelectedItem();
        if (origin == null) {
            return;
        }

        this.activeRulesController.addRule(origin.getRule().newInstance());
    }

    //endregion
}
