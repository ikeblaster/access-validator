package cz.zcu.kiv.accessvalidator.components;

import cz.zcu.kiv.accessvalidator.validator.rules.ComplexRule;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.Comparator;

/**
 * @author ike
 */
public class LibraryController {

    @FXML
    public ListView<Rule> rulesLibrary;

    private ObservableList<Rule> rulesLibraryItems = FXCollections.observableArrayList();
    private ActiveRulesController activeRulesController;



    //region================== GUI initialization ==================

    public void onLoad(Stage stage, ActiveRulesController activeRulesController) {
        this.activeRulesController = activeRulesController;
    }

    @FXML
    public void initialize() {
        this.rulesLibraryItems.add(new GroupRule(false));
        this.rulesLibraryItems.add(new ComplexRule());

        FXCollections.sort(this.rulesLibraryItems, Comparator.comparing(Rule::toString));
        this.rulesLibrary.setItems(this.rulesLibraryItems);

        this.rulesLibrary.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                Rule origin = this.rulesLibrary.getSelectionModel().getSelectedItem();
                if (origin == null) {
                    return;
                }

                this.activeRulesController.addRule(origin.newInstance());

            }
        });
    }

    //endregion

}
