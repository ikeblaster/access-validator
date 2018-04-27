package cz.zcu.kiv.accessvalidator.configurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Heart of the application where every necessary variable is initialized,
 * including GUI. The application starts from here.
 */
public class Configurator extends Application {

    /**
     * Main method that starts the application.
     *
     * @param args Starting arguments (currently none are supported).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overriden method that opens the active window in which the application is being drawn.
     *
     * @param primaryStage Parent window, by default OS window manager.
     * @throws Exception When an unexpected event occured (could be anything).
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        ConfiguratorController controller = loader.getController();
        controller.onLoad(primaryStage);

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/appicon.png")));
        primaryStage.setTitle("AccessValidator - formální kontrola ACCDB databází");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
}