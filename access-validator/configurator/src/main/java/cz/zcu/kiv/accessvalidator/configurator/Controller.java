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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import org.controlsfx.control.PropertySheet;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;


public class Controller {

    @FXML
    private ListView<Rule> rulesLibrary;

    @FXML
    private TreeView<Rule> activeRules;

    @FXML
    private PropertySheet details;



    private TreeItemRuleAdaptor activeRulesRoot;



    //region================== GUI initialization ==================

    @FXML
    public void initialize() {
        this.initializeRulesLibrary();
        this.initializeActiveRules();
        this.initializeDetails();

        //platform.runLater(this::refreshInputFiles);
    }


    private void initializeRulesLibrary() {
        ObservableList<Rule> rulesLibraryItems = FXCollections.observableArrayList();

        rulesLibraryItems.add(new GroupRule(false));
        rulesLibraryItems.add(new ComplexRule());

        FXCollections.sort(rulesLibraryItems, Comparator.comparing(Rule::toString));
        this.rulesLibrary.setItems(rulesLibraryItems);

        this.rulesLibrary.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                Rule origin = this.rulesLibrary.getSelectionModel().getSelectedItem();
                if(origin == null) {
                    return;
                }

                Rule toBeAdded = origin.newInstance();
                this.addRulesListeners(toBeAdded);

                TreeItemRuleAdaptor parent = (TreeItemRuleAdaptor) this.activeRules.getSelectionModel().getSelectedItem();
                if(parent == null || !parent.isGroup()) {
                    parent = this.activeRulesRoot;
                }

                parent.addChild(toBeAdded);
            }
        });
    }


    private void initializeActiveRules() {
        this.initEmptyRules();

        this.activeRules.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.details.getItems().clear();

            if(newValue instanceof TreeItemRuleAdaptor) {
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
    public void actionNewFile() {
        this.initEmptyRules();
    }

    public void actionOpenFile() {
        try {
            RulesSerializer serializer = new RulesSerializer();
            Rule root = serializer.deserialize(new FileInputStream("rules.xml"));
            this.initLoadedRules(root);
        } catch(XMLStreamException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionSaveFile() {
        try {
            RulesSerializer serializer = new RulesSerializer();
            serializer.serialize(this.activeRulesRoot.getValue(), new FileOutputStream("rules.xml"));
        } catch(XMLStreamException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionSaveAs() {
    }

    public void actionExitApp() {
        Platform.exit();
    }

    public void actionSetTestFile() {
    }

    public void actionTestRules() {

        try {
            AccdbValidator validator = new AccdbValidator(new File("data/databaze.accdb"));

            if(validator.validate(this.activeRulesRoot.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Výsledek kontroly");
                alert.setHeaderText("");
                alert.setContentText("Databáze splňuje pravidla");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                alert.setTitle("Výsledek kontroly");
                alert.setHeaderText("");
                alert.setContentText("Databáze nesplňuje pravidla");
                alert.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionDeleteRule() {
    }

    //endregion




    private void initEmptyRules() {
        this.initLoadedRules(new GroupRule());
    }

    private void initLoadedRules(Rule root) {
        this.activeRulesRoot = new TreeItemRuleAdaptor(root);
        this.activeRules.setRoot(this.activeRulesRoot);
        this.addRulesListeners(this.activeRulesRoot.getValue());
    }

    private void addRulesListeners(Rule rule) {
        if(rule instanceof Monitorable) {
            rule.onChange(o -> this.activeRules.refresh());
        }
        if(rule instanceof GroupRule) {
            GroupRule groupRule = (GroupRule) rule;
            for(Rule child : groupRule.getRules()) {
                this.addRulesListeners(child);
            }
        }
    }






}