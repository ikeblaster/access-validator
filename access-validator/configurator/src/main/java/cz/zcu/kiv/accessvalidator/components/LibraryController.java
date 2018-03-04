package cz.zcu.kiv.accessvalidator.components;

import cz.zcu.kiv.accessvalidator.validator.RulesRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * @author ike
 */
public class LibraryController {

    @FXML
    public ListView<Rule> rules;

    private ActiveRulesController activeRulesController;


    //region================== GUI initialization ==================

    public void onLoad(Stage stage, ActiveRulesController activeRulesController) {
        this.activeRulesController = activeRulesController;
    }

    @FXML
    public void initialize() {
        this.rules.getItems().addAll(RulesRepository.getAll());
        //FXCollections.sort(this.rules.getItems(), Comparator.comparing(Rule::toString));

        this.rules.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Rule origin = this.rules.getSelectionModel().getSelectedItem();
                if (origin == null) {
                    return;
                }

                this.activeRulesController.addRule(origin.newInstance());
            }
        });
    }

    //endregion

}
