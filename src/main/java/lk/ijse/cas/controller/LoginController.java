package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.LoginBO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.util.WindowController;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public JFXTextField txtFieldUserID;
    @FXML
    public JFXPasswordField txtFieldPassword;
    @FXML
    private StackPane rootNode;
    @FXML
    private Rectangle loginBoxShape;

    private WindowController windowController;

    private UserDTO user;

    private double xOffset = 0;
    private double yOffset = 0;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);



    public void initialize() {
        Image img = new Image("/lk.ijse.cas/asserts/images/Login_box_side.jpg");
        loginBoxShape.setFill(new ImagePattern(img));
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String userId = txtFieldUserID.getText();
        String password = txtFieldPassword.getText();

        if (userId != null && !userId.isEmpty() && password != null && !password.isEmpty()) {
            try {
                checkCredential(userId, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter all mandatory details!").show();
        }
    }

    private void checkCredential(String userID, String password) throws SQLException, IOException, ClassNotFoundException {
        UserDTO userDTO = loginBO.searchUserById(userID);

        if (userDTO != null) {
            if (BCrypt.checkpw(password, userDTO.getPassword())) {
                user = userDTO;
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User ID not found!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/lk.ijse.cas/Dashboard_page.fxml"));
        AnchorPane dashboardRoot = loader.load();

        DashboardController dashboardController = loader.getController();

        dashboardController.setUser(user); // Pass the User object to the DashboardController

        dashboardController.setWindowController(windowController);

        Stage dashboardStage = new Stage();

        Scene scene = new Scene(dashboardRoot);
        dashboardStage.initStyle(StageStyle.TRANSPARENT);
        dashboardStage.setScene(scene);
        dashboardStage.centerOnScreen();
        dashboardStage.setTitle("Dashboard");

        // Get the current stage and close it when the new stage is shown
        Stage currentStage = (Stage) rootNode.getScene().getWindow();
        dashboardStage.setOnShown(event -> currentStage.close());

        dashboardStage.show();
    }

    @FXML
    void btnForgotPassOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/lk.ijse.cas/Forgot_Password_page.fxml"));

        Stage stage = new Stage();

        StackPane registerRoot = null;
        try {
            registerRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Rectangle clip = new Rectangle(958, 622);
        clip.setArcHeight(90);
        clip.setArcWidth(90);

        registerRoot.setClip(clip);

        ForgotPasswordController forgotPasswordController = loader.getController();

        WindowController windowController = new WindowController();
        forgotPasswordController.setWindowController(windowController);

        Image img = new Image("/lk.ijse.cas/asserts/images/Login_box_side.jpg");
        forgotPasswordController.setLoginBoxShape(img);

        Scene scene = new Scene(registerRoot);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();

        // Get the current stage and close it when the new stage is shown
        Stage currentStage = (Stage) rootNode.getScene().getWindow();

        // Create a transition animation
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(10), currentStage.getScene().getRoot());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished(e -> {
            currentStage.hide(); // Hide the current stage
            stage.show(); // Show the registration stage

            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(10), stage.getScene().getRoot());
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        });
        fadeOutTransition.play();
    }

    @FXML
    void btnCloseOnMouseClicked(MouseEvent event) {
        Stage stage = (Stage) rootNode.getScene().getWindow();
        if (windowController != null) {
            windowController.close(stage);
        }
    }

    @FXML
    void btnMinimizeOnMouseClicked(MouseEvent event) {
        Stage stage = (Stage) rootNode.getScene().getWindow();
        if (windowController != null) {
            windowController.minimize(stage);
        }
    }

    @FXML
    void OnMousePressed(MouseEvent event) {
// Record the mouse position relative to the window
        Stage stage = (Stage) rootNode.getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void OnMouseDragged(MouseEvent event) {
// Move the window by the mouse delta
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}
