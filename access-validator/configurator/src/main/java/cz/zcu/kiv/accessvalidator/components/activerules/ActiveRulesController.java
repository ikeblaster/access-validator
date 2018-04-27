package cz.zcu.kiv.accessvalidator.components.activerules;

import cz.zcu.kiv.accessvalidator.common.Dialogs;
import cz.zcu.kiv.accessvalidator.common.FileChooserEx;
import cz.zcu.kiv.accessvalidator.components.details.DetailsController;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.serialization.RulesSerializer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller for ActiveRules pane in JavaFX GUI.
 * Contains a tree with currently active set of rules.
 *
 * @author ike
 */
public class ActiveRulesController {

    /**
     * Tree panel with currently active rules.
     */
    @FXML
    private TreeView<Rule> activeRules;

    /**
     * File chooser for opening or saving files with rules.
     */
    private FileChooserEx rulesFileChooser = new FileChooserEx(this.getClass().getSimpleName());

    /**
     * State of pane, tells whether the loaded rules have been changed and need to be saved.
     */
    private boolean rulesChanged = false;

    /**
     * Controller of the details pane (which contains properties of highlighted rule).
     */
    private DetailsController detailsController;

    /**
     * Root item of the tree.
     */
    private TreeItemRuleAdaptor activeRulesRoot;


    //region================== GUI initialization ==================

    /**
     * Called after GUI load. Sets parent stage and dependency to other parts of GUI.
     *
     * @param stage Parent stage for pane.
     * @param detailsController Controller of the details pane.
     */
    public void onLoad(Stage stage, DetailsController detailsController) {
        this.rulesFileChooser.setStage(stage);
        this.detailsController = detailsController;
    }

    /**
     * Called during GUI initialization.
     * Initializes elements inside pane (adds listeners, filter for file chooser, etc.).
     */
    @FXML
    public void initialize() {
        this.loadEmptyRules();

        this.activeRules.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedItem) -> {
            this.detailsController.clear();

            if (selectedItem instanceof TreeItemRuleAdaptor) {
                TreeItemRuleAdaptor treeItem = (TreeItemRuleAdaptor) selectedItem;
                this.detailsController.addAll(treeItem.getPropertySheetItems());
            }
        });

        this.rulesFileChooser.addExtensionFilter(new FileChooser.ExtensionFilter("Soubor pravidel", "*.xml"));
    }
    //endregion


    //region================== Actions (buttons) ==================

    /**
     * Creates a new set of rules.
     */
    public void actionNewFile() {
        if(!this.canDiscardFile()) {
            return;
        }
        this.rulesFileChooser.setFile(null);
        this.loadEmptyRules();
    }

    /**
     * Opens an existing file with rules nad loads them into the tree.
     */
    public void actionOpenFile() {
        if(!this.canDiscardFile() || !this.rulesFileChooser.openFile()) {
            return;
        }
        try {
            RulesSerializer serializer = new RulesSerializer();
            Rule root = serializer.deserialize(new FileInputStream(this.rulesFileChooser.getFile()));
            this.loadRules(root);
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves current set of rules.
     */
    public void actionSaveFile() {
        if(!this.rulesFileChooser.hasFile()) {
            this.actionSaveAs();
            return;
        }
        try {
            RulesSerializer serializer = new RulesSerializer();
            FileOutputStream outputStream = new FileOutputStream(this.rulesFileChooser.getFile());
            serializer.serialize(this.activeRulesRoot.getValue(), outputStream);
            outputStream.close();
            this.rulesChanged = false;
        } catch (XMLStreamException | IOException e) {
            Dialogs.showErrorBox("Soubor se nepodařilo uložit", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves the current set of rules as a different file.
     */
    public void actionSaveAs() {
        if(this.rulesFileChooser.saveFile()) {
            this.actionSaveFile();
        }
    }

    /**
     * Deletes highlighted rule.
     */
    public void actionDeleteRule() {
        TreeItem<Rule> selected = this.activeRules.getSelectionModel().getSelectedItem();
        if (selected == null || !(selected.getParent() instanceof TreeItemRuleAdaptor)) {
            return;
        }

        TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) selected.getParent();
        parent.removeChild(selected);
    }

    //endregion

    /**
     * Gets the root rule of the tree.
     * @return Root rule.
     */
    public Rule getRootRule() {
        return this.activeRulesRoot.getValue();
    }

    /**
     * Adds a rule into highlighted parent.
     *
     * @param rule Rule to be added.
     */
    public void addRule(Rule rule) {
        TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) this.activeRules.getSelectionModel().getSelectedItem();
        if (parent == null || !parent.isGroup()) {
            parent = this.activeRulesRoot;
        }

        parent.addChild(rule);
        this.rulesChanged = true;
    }


    /**
     * Clears out the tree.
     */
    private void loadEmptyRules() {
        this.loadRules(new GroupRule());
    }

    /**
     * Loads a rule into the tree (clears out previous).
     *
     * @param root New rule root.
     */
    private void loadRules(Rule root) {
        this.rulesChanged = false;
        this.activeRulesRoot = new TreeItemRuleAdaptor(root);
        this.activeRules.setRoot(this.activeRulesRoot);
    }


    /**
     * Tests whether it's possible to discard current rule set.
     * Asks user for confirmation when set was changed.
     *
     * @return {@code true} if current rule set can be cleared/discareded.
     */
    private boolean canDiscardFile(){
        if(!this.rulesChanged) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("");
        alert.setHeaderText("Chcete zavřít aktuální soubor pravidel bez uložení?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}
