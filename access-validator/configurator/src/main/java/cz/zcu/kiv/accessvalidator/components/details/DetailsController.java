package cz.zcu.kiv.accessvalidator.components.details;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import java.util.Collection;

/**
 * @author ike
 */
public class DetailsController {

    public PropertySheet details;


    public void onLoad(Stage stage) {
    }

    @FXML
    public void initialize() {
        this.details.setPropertyEditorFactory(param -> ((PropertySheetRuleAdaptor) param).getPropertyEditor());
    }

    public void clear() {
        this.details.getItems().clear();
    }

    public void addAll(Collection<PropertySheetRuleAdaptor> items) {
        this.details.getItems().addAll(items);
    }

}
