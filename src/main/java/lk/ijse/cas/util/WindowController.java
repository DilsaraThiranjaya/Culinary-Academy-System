package lk.ijse.cas.util;

import javafx.stage.Stage;

public class WindowController {
    public void close (Stage stage){
        stage.close();
    }

    public void minimize (Stage stage){
        stage.setIconified(true);
    }
}
