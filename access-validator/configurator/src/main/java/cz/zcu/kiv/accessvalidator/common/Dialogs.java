package cz.zcu.kiv.accessvalidator.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Helper class for showing simple dialog windows.
 *
 * @author ike
 */
public class Dialogs {

    /**
     * Show dialog window with an error.
     *
     * @param header Header text of the window.
     * @param message Message text of the window.
     */
    public static void showErrorBox(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        alert.setTitle("Chyba");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
