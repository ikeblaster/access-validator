package cz.zcu.kiv.accessvalidator.configurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Heart of the application where every necessary variable is initialized,
 * including GUI. The application starts from here.
 */
public class main extends Application {

    /**
     * Constant variable that tracks events that happened during the run.
     */
    //private static final Logger log = LogManager.getLogger();

    /**
     * Main method that starts the application.
     *
     * @param args Starting arguments (currently none are supported).
     */
    public static void main(String[] args) {
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

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/editor.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/editor.css").toExternalForm());

        //Controller controller = loader.getController();
        //controller.onLoad(primaryStage);

        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/app.png")));
        primaryStage.setTitle("AccessValidator - konfigurátor pravidel");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
}