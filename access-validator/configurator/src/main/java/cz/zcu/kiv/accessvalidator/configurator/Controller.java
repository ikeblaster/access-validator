package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.validator.AccdbValidator;
import cz.zcu.kiv.accessvalidator.validator.rules.ComplexRule;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.annotations.Monitorable;
import cz.zcu.kiv.accessvalidator.validator.rules.serialization.RulesSerializer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;


public class Controller {

    @FXML
    private ListView<Rule> rulesLibrary;
    @FXML
    private TreeView<Rule> activeRules;
    @FXML
    private PropertySheet details;

    private Stage stage;
    private TreeItemRuleAdaptor activeRulesRoot;
    private ObservableList<Rule> rulesLibraryItems = FXCollections.observableArrayList();

    private FileChooserEx rulesFileChooser = new FileChooserEx();
    private FileChooserEx testDatabaseFileChooser = new FileChooserEx();
    private boolean rulesChanged = false;


    //region================== GUI initialization ==================


    public void setStage(Stage stage) {
        this.stage = stage;
        this.rulesFileChooser.setStage(stage);
        this.testDatabaseFileChooser.setStage(stage);
    }

    @FXML
    public void initialize() {

        this.initializeFields();
        this.initializeRulesLibrary();
        this.initializeActiveRules();
        this.initializeDetails();

        //platform.runLater(this::refreshInputFiles);
    }

    private void initializeFields() {
        this.rulesLibraryItems.add(new GroupRule(false));
        this.rulesLibraryItems.add(new ComplexRule());
        this.testDatabaseFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Access databáze", "*.accdb", "*.mdb"));
        this.rulesFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor pravidel", "*.xml"));
    }

    private void initializeRulesLibrary() {

        FXCollections.sort(this.rulesLibraryItems, Comparator.comparing(Rule::toString));
        this.rulesLibrary.setItems(this.rulesLibraryItems);

        this.rulesLibrary.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                Rule origin = this.rulesLibrary.getSelectionModel().getSelectedItem();
                if (origin == null) {
                    return;
                }

                Rule toBeAdded = origin.newInstance();
                this.addRulesListeners(toBeAdded);

                TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) this.activeRules.getSelectionModel().getSelectedItem();
                if (parent == null || !parent.isGroup()) {
                    parent = this.activeRulesRoot;
                }

                parent.addChild(toBeAdded);
                this.rulesChanged = true;
            }
        });
    }


    private void initializeActiveRules() {
        this.loadEmptyRules();

        this.activeRules.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.details.getItems().clear();

            if (newValue instanceof TreeItemRuleAdaptor) {
                TreeItemRuleAdaptor treeItem = (TreeItemRuleAdaptor) newValue;
                this.details.getItems().addAll(treeItem.getPropertySheetItems());
            }
        });
    }

    private void initializeDetails() {
        this.details.setPropertyEditorFactory(param -> ((PropertySheetRuleAdaptor) param).getPropertyEditor());
    }


    //endregion


    //region================== Actions (buttons) ==================


    @FXML
    public void actionExitApp() {
        Platform.exit();
    }

    @FXML
    public void actionNewFile() {
        if(!this.canDiscardFile()) {
            return;
        }
        this.rulesFileChooser.setFile(null);
        this.loadEmptyRules();
    }

    @FXML
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

    @FXML
    public void actionSaveFile() {
        if(!this.rulesFileChooser.hasFile()) {
            this.actionSaveAs();
            return;
        }
        try {
            RulesSerializer serializer = new RulesSerializer();
            serializer.serialize(this.activeRulesRoot.getValue(), new FileOutputStream(this.rulesFileChooser.getFile()));
            this.rulesChanged = false;
        } catch (XMLStreamException | IOException e) {
            this.showErrorBox("Soubor se nepodařilo uložit", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void actionSaveAs() {
        if(this.rulesFileChooser.saveFile()) {
            this.actionSaveFile();
        }
    }

    @FXML
    public void actionSetTestFile() {
        this.testDatabaseFileChooser.openFile();
    }

    @FXML
    public void actionTestRules() {
        if (!this.testDatabaseFileChooser.hasFile()) {
            if (!this.testDatabaseFileChooser.openFile()) {
                return;
            }
        }

        try {
            AccdbValidator validator = new AccdbValidator(this.testDatabaseFileChooser.getFile());

            if (validator.validate(this.activeRulesRoot.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Výsledek kontroly");
                alert.setHeaderText("");
                alert.setContentText("Databáze splňuje pravidla");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                alert.setTitle("Výsledek kontroly");
                alert.setHeaderText("");
                alert.setContentText("Databáze nesplňuje pravidla");
                alert.showAndWait();
            }

        } catch (IOException e) {
            this.showErrorBox("Kontrolu se nepodařilo zahájit", e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    @FXML
    public void actionDeleteRule() {
        TreeItem<Rule> selected = this.activeRules.getSelectionModel().getSelectedItem();
        if (selected == null || !(selected.getParent() instanceof TreeItemRuleAdaptor)) {
            return;
        }

        TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) selected.getParent();
        parent.removeChild(selected);
    }


    //endregion


    //region================== Common methods ==================
    private void loadEmptyRules() {
        this.loadRules(new GroupRule());
    }

    private void loadRules(Rule root) {
        this.rulesChanged = false;
        this.activeRulesRoot = new TreeItemRuleAdaptor(root);
        this.activeRules.setRoot(this.activeRulesRoot);
        this.addRulesListeners(this.activeRulesRoot.getValue());
    }

    private void addRulesListeners(Rule rule) {
        if (rule instanceof Monitorable) {
            rule.onChange(o -> this.activeRules.refresh());
        }
        if (rule instanceof GroupRule) {
            GroupRule groupRule = (GroupRule) rule;
            for (Rule child : groupRule.getRules()) {
                this.addRulesListeners(child);
            }
        }
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
    //endregion



    //region================== Dialog helpers ==================
    private void showErrorBox(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        alert.setTitle("Chyba");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //endregion

}