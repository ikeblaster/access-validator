package cz.zcu.kiv.accessvalidator.configurator;

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
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.function.UnaryOperator;


public class Controller {

    @FXML
    private ListView<Rule> rulesLibrary;

    @FXML
    private TreeView<Rule> activeRules;

    @FXML
    private PropertySheet details;

    @FXML
    private MenuItem buttonNew;

    @FXML
    private MenuItem buttonOpen;

    @FXML
    private MenuItem buttonSave;

    @FXML
    private MenuItem buttonSaveAs;

    @FXML
    private MenuItem buttonExit;


    private TreeItemRuleAdaptor activeRulesRoot;



    //region================== GUI initialization ==================

    @FXML
    public void initialize() {
        this.initializeRulesLibrary();
        this.initializeActiveRules();
        this.initializeMenuButtons();
        this.initializeDetails();

        //platform.runLater(this::refreshInputFiles);
    }
    private void initializeMenuButtons() {

        this.buttonNew.setOnAction(e -> this.initEmptyRules());

        this.buttonOpen.setOnAction(e -> {
            try {
                RulesSerializer serializer = new RulesSerializer();
                Rule root = serializer.deserialize(new FileInputStream("rules.xml"));
                this.initLoadedRules(root);
            } catch(XMLStreamException | IOException ex) {
                ex.printStackTrace();
            }
        });

        this.buttonSave.setOnAction(e -> {
            try {
                RulesSerializer serializer = new RulesSerializer();
                serializer.serialize(activeRulesRoot.getValue(), new FileOutputStream("rules.xml"));
            } catch(XMLStreamException | IOException ex) {
                ex.printStackTrace();
            }
        });

        this.buttonExit.setOnAction(e -> Platform.exit());

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


    //region Old stuff
    private Integer checkTimeValue(Spinner<Integer> spinner, Object newValue) {
        if (newValue == null) {
            spinner.getValueFactory().setValue(0);
            return 0;
        }
        return (int) newValue;
    }

    private TextFormatter<String> textNumberFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            /* max 2 digit */
            if (change.getCaretPosition() > 2) {
                return null;
            }

            /* only digits */
            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        return new TextFormatter<>(filter);
    }


    public void onLoad(Stage primaryStage) {
        Platform.runLater(() -> primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> this.onWindowFocusChange(newValue)));
    }

    private void onWindowFocusChange(Boolean isFocused) {
        if (isFocused) {

        }
    }

    @FXML
    public void onInputFileChange() {
//        if (inputFile.getSelectionModel().isEmpty()) {
//            return; // also triggered during file menu update
//        }
/*
        try {
            FileWrapper file = inputFile.getSelectionModel().getSelectedItem();

            if(Objects.equals(file, loadedFile)) {
                return; // cancel, same file
            }

            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file.getFile()),
                    Charset.forName("windows-1250")
            );

            CsvReader csvReader = new CsvReader(new BufferedReader(reader));
            busStop = csvReader.getBusStop(); // get loaded bus stop data from cscReader
            loadedFile = file;

            reader.close();

            // Show title for current file
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d. M. y"); // time format
            tableTitle.setText(busStop.getName() + " (" + busStop.getDateRange().getFrom().getDate().format(dtf) + " - " + busStop.getDateRange().getTo().getDate().format(dtf) + ")");

            log.info("CSV file parsed.");
        } catch(Exception e) {
            resetLoadedData();
            tablePlaceholder.setText(placeholderTexts.CSV_ERROR.toString());
            log.error("CSV parser error", e);
        }
*/
    }
    //endregion


}