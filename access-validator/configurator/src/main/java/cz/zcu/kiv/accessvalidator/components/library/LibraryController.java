package cz.zcu.kiv.accessvalidator.components.library;

import cz.zcu.kiv.accessvalidator.components.activerules.ActiveRulesController;
import cz.zcu.kiv.accessvalidator.validator.RulesRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * @author ike
 */
public class LibraryController {

    @FXML
    public ListView<ListViewRuleAdaptor> rules;

    private ActiveRulesController activeRulesController;


    //region================== GUI initialization ==================

    public void onLoad(Stage stage, ActiveRulesController activeRulesController) {
        this.activeRulesController = activeRulesController;
    }

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

    public void actionAddSelectedRule() {
        ListViewRuleAdaptor origin = this.rules.getSelectionModel().getSelectedItem();
        if (origin == null) {
            return;
        }

        this.activeRulesController.addRule(origin.getRule().newInstance());
    }

    //endregion
}
