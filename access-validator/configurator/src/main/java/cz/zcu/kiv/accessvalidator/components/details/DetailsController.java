package cz.zcu.kiv.accessvalidator.components.details;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import java.util.Collection;

/**
 * Controller for Details pane in JavaFX UI.
 * Contains a property sheet with properties for currently highlighted rule.
 *
 * @author ike
 */
public class DetailsController {

    /**
     * Property sheet with properties for currently highlighted rule.
     */
    public PropertySheet details;

    /**
     * Called after GUI load. Sets parent stage.
     *
     * @param stage Parent stage for pane.
     */
    public void onLoad(Stage stage) {
    }

    /**
     * Called during GUI initialization.
     * Initializes elements inside pane (sets factory for properties).
     */
    @FXML
    public void initialize() {
        this.details.setPropertyEditorFactory(property -> ((PropertySheetRuleAdaptor) property).getPropertyEditor());
    }

    /**
     * Removes all property editors from GUI.
     */
    public void clear() {
        this.details.getItems().clear();
    }

    /**
     * Adds property editors to GUI.
     *
     * @param items Set of {@code PropertySheet} items.
     */
    public void addAll(Collection<PropertySheetRuleAdaptor> items) {
        this.details.getItems().addAll(items);
    }

}
