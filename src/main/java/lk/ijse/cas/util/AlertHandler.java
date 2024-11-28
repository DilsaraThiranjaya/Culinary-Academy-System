package lk.ijse.cas.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHandler {
    public static void showInfo(String message) {
        showAlert(AlertType.INFORMATION, "Information", message);
    }

    public static void showWarning(String message) {
        showAlert(AlertType.WARNING, "Warning", message);
    }

    public static void showError(String message) {
        showAlert(AlertType.ERROR, "Error", message);
    }

    private static void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
