package cz.zcu.kiv.accessvalidator.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @author ike
 */
public class Dialogs {
    public static void showErrorBox(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        alert.setTitle("Chyba");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
