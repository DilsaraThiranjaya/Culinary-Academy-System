package lk.ijse.cas.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import lk.ijse.cas.exceptions.AuthenticationException;
import lk.ijse.cas.exceptions.DatabaseException;
import lk.ijse.cas.exceptions.ResourceNotFoundException;
import lk.ijse.cas.util.AlertHandler;
import lk.ijse.cas.util.WindowController;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private MFXPasswordField txtFieldPassword;

    @FXML
    private MFXTextField txtFieldUserID;

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
        try {
            Image img = new Image("/lk.ijse.cas/asserts/images/Login_box_side.jpg");
            loginBoxShape.setFill(new ImagePattern(img));
        } catch (Exception e) {
            AlertHandler.showError("Failed to load login box image: " + e.getMessage());
        }
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String userId = txtFieldUserID.getText();
        String password = txtFieldPassword.getText();

        if (userId != null && !userId.isEmpty() && password != null && !password.isEmpty()) {
            try {
                checkCredential(userId, password);
            } catch (SQLException e) {
                AlertHandler.showError("Database error occurred: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                AlertHandler.showError("Class not found: " + e.getMessage());
            } catch (AuthenticationException e) {
                AlertHandler.showError(e.getMessage());
            } catch (ResourceNotFoundException e) {
                AlertHandler.showError(e.getMessage());
            } catch (Exception e) {
                AlertHandler.showError("An unexpected error occurred: " + e.getMessage());
            }
        } else {
            AlertHandler.showWarning("Please enter all mandatory details!");
        }
    }

    private void checkCredential(String userID, String password) throws SQLException, ClassNotFoundException, AuthenticationException, ResourceNotFoundException, DatabaseException {
        try {
            UserDTO userDTO = loginBO.searchUserById(userID);

            if (userDTO != null) {
                if (BCrypt.checkpw(password, userDTO.getPassword())) {
                    user = userDTO;
                    navigateToTheDashboard();
                } else {
                    throw new AuthenticationException("Password is incorrect!");
                }
            } else {
                throw new ResourceNotFoundException("User ID not found!");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve user data from the database.", e);
        } catch (ClassNotFoundException e) {
            throw new ResourceNotFoundException("Error while loading required resources: " + e.getMessage(), e);
        } catch (Exception e) {
            AlertHandler.showError("Unexpected error during credential check: " + e.getMessage());
        }
    }

    private void navigateToTheDashboard() {
        try {
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
        } catch (IOException e) {
            AlertHandler.showError("Error navigating to the dashboard: " + e.getMessage());
        } catch (Exception e) {
            AlertHandler.showError("Unexpected error while navigating to the dashboard: " + e.getMessage());
        }
    }

    @FXML
    void btnForgotPassOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/lk.ijse.cas/Forgot_Password_page.fxml"));
            Stage stage = new Stage();

            StackPane registerRoot = loader.load();

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
        } catch (IOException e) {
            AlertHandler.showError("Error loading forgot password page: " + e.getMessage());
        } catch (Exception e) {
            AlertHandler.showError("Unexpected error while processing the forgot password page: " + e.getMessage());
        }
    }

    @FXML
    void btnCloseOnMouseClicked(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            if (windowController != null) {
                windowController.close(stage);
            }
        } catch (Exception e) {
            AlertHandler.showError("Error closing the application: " + e.getMessage());
        }
    }

    @FXML
    void btnMinimizeOnMouseClicked(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            if (windowController != null) {
                windowController.minimize(stage);
            }
        } catch (Exception e) {
            AlertHandler.showError("Error minimizing the application: " + e.getMessage());
        }
    }

    @FXML
    void OnMousePressed(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } catch (Exception e) {
            AlertHandler.showError("Error tracking mouse press: " + e.getMessage());
        }
    }

    @FXML
    void OnMouseDragged(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } catch (Exception e) {
            AlertHandler.showError("Error dragging the window: " + e.getMessage());
        }
    }
}
