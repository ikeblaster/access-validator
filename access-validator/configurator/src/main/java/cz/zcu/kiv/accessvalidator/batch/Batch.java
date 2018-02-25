package cz.zcu.kiv.accessvalidator.batch;

import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.serialization.RulesSerializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;


/**
 * Heart of the application where every necessary variable is initialized,
 * including GUI. The application starts from here.
 */
public class Batch extends Application {

    /**
     * Constant variable that tracks events that happened during the run.
     */
    //private static final Logger log = LogManager.getLogger();
    private Rule rule;
    private Stage parent;

    /**
     * Main method that starts the application.
     *
     * @param args Starting arguments (currently none are supported).
     */
    public static void main(String[] args) throws Exception {
        RulesSerializer serializer = new RulesSerializer();
        Rule rule = serializer.deserialize(new FileInputStream("rules.xml"));

        launch(args);
    }



    /**
     * Overriden method that draws the active window in which the application is beeing drawn.
     *
     * @param primaryStage Basic parameter in which all information about the drawn window are stored.
     * @throws Exception When an unexpected event occured (could be anything).
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //log.info("Start");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/batch.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        BatchController controller = loader.getController();
        controller.onLoad(primaryStage, this.rule);

        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/app.png")));
        primaryStage.setTitle("AccessValidator - hromadná kontrola");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(300);

        if(this.parent != null) {
            primaryStage.initOwner(this.parent);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
        }

        primaryStage.show();
    }


    public void start(Rule rule, Stage parent) throws Exception {
        this.rule = rule;
        this.parent = parent;

        this.start(new Stage());
    }

}