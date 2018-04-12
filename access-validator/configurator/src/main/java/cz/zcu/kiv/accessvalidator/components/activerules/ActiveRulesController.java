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
 * @author ike
 */
public class ActiveRulesController {

    @FXML
    private TreeView<Rule> activeRules;

    private FileChooserEx rulesFileChooser = new FileChooserEx(this.getClass().getSimpleName());
    private boolean rulesChanged = false;
    private DetailsController detailsController;
    private TreeItemRuleAdaptor activeRulesRoot;


    //region================== GUI initialization ==================

    public void onLoad(Stage stage, DetailsController detailsController) {
        this.rulesFileChooser.setStage(stage);
        this.detailsController = detailsController;
    }

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

    public void actionNewFile() {
        if(!this.canDiscardFile()) {
            return;
        }
        this.rulesFileChooser.setFile(null);
        this.loadEmptyRules();
    }

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

    public void actionSaveAs() {
        if(this.rulesFileChooser.saveFile()) {
            this.actionSaveFile();
        }
    }

    public void actionDeleteRule() {
        TreeItem<Rule> selected = this.activeRules.getSelectionModel().getSelectedItem();
        if (selected == null || !(selected.getParent() instanceof TreeItemRuleAdaptor)) {
            return;
        }

        TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) selected.getParent();
        parent.removeChild(selected);
    }

    //endregion


    public Rule getRootRule() {
        return this.activeRulesRoot.getValue();
    }

    public void addRule(Rule rule) {
        TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) this.activeRules.getSelectionModel().getSelectedItem();
        if (parent == null || !parent.isGroup()) {
            parent = this.activeRulesRoot;
        }

        parent.addChild(rule);
        this.rulesChanged = true;
    }



    private void loadEmptyRules() {
        this.loadRules(new GroupRule());
    }

    private void loadRules(Rule root) {
        this.rulesChanged = false;
        this.activeRulesRoot = new TreeItemRuleAdaptor(root);
        this.activeRules.setRoot(this.activeRulesRoot);
    }



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
