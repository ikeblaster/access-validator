package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.configurator.rules.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.Editors;

import java.util.Collection;
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
    public void initialize() {

        ObservableList<Rule> rulesLibraryItems = FXCollections.observableArrayList();

        rulesLibraryItems.add(new TableExistsRule());
        rulesLibraryItems.add(new TableCountRule());
        rulesLibraryItems.add(new ExistRule());


        this.activeRules.setShowRoot(false);
        this.activeRules.setRoot(new TreeItem<>(new AndGroupRule()));

        FXCollections.sort(rulesLibraryItems, Comparator.comparing(Rule::toString));
        this.rulesLibrary.setItems(rulesLibraryItems);

        this.rulesLibrary.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                Rule selectedRule = this.rulesLibrary.getSelectionModel().getSelectedItem();
                if(selectedRule != null) {
                    this.activeRules.getRoot().getChildren().add(new TreeItem<>(selectedRule.newInstance()));
                }
            }
        });

        this.details.setPropertyEditorFactory(param -> {
            if(param.getValue() instanceof Collection) {
                return Editors.createChoiceEditor(param, (Collection) param.getValue());
            } else if (param.getValue() instanceof Boolean) {
                return Editors.createCheckEditor(param);
            } else if (param.getValue() instanceof Integer) {
                return Editors.createNumericEditor(param);
            } else {
                return Editors.createTextEditor(param);
            }
        });

        this.activeRules.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Rule rule = newValue.getValue();
            this.details.getItems().clear();

            for (RuleProperty property : rule.getProperties()) {
                this.details.getItems().add(new PropertySheetRuleAdaptor(property));
            }
        });



        //platform.runLater(this::refreshInputFiles);
    }

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
        Platform.runLater(() -> primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> onWindowFocusChange(newValue)));
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


}