package lk.ijse.cas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.cas.controller.LoginController;
import lk.ijse.cas.util.SessionFactoryConfig;
import lk.ijse.cas.util.WindowController;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Launcher extends Application {
    public static void main(String[] args) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
//            String[] sqlQueries = new String(Files.readAllBytes(Paths.get("src/main/resources/import.sql")), StandardCharsets.UTF_8).split(";");
//
//            for (String query : sqlQueries) {
//                if (query.trim().length() > 0) {
//                    session.createNativeQuery(query.trim()).executeUpdate();
//                }
//            }
//
//            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        } finally {
            session.close();
        }

        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/lk.ijse.cas/Login_page.fxml"));

        stage = new Stage();

        StackPane loginRoot = loader.load();

        Rectangle clip = new Rectangle(958, 622);
        clip.setArcHeight(90);
        clip.setArcWidth(90);

        loginRoot.setClip(clip);

        LoginController loginController = loader.getController();

        WindowController windowController = new WindowController();
        loginController.setWindowController(windowController);

        Scene scene = new Scene(loginRoot);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Login_page");
        stage.centerOnScreen();
        stage.show();

    }
}
