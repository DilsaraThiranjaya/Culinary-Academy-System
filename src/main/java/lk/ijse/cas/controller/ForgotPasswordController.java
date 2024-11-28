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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.ForgotPasswordBO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.util.EmailSender;
import lk.ijse.cas.util.WindowController;
import lk.ijse.cas.util.Regex;
import lk.ijse.cas.util.TextField;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private Rectangle loginBoxShape;

    @FXML
    private StackPane rootNode;

    @FXML
    private MFXPasswordField txtFieldConfirmPassword;

    @FXML
    private MFXPasswordField txtFieldPassword;

    @FXML
    private MFXTextField txtFieldUserid;

    @FXML
    private MFXTextField txtOtpCode;

    private WindowController windowController;

    private double xOffset = 0;
    private double yOffset = 0;

    private int otpCode;

    ForgotPasswordBO forgotPasswordBO = (ForgotPasswordBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FORGOT_PASSWORD);

    public void initialize() {
        otpCode = -1;
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public void setLoginBoxShape(Image img) {
        loginBoxShape.setFill(new ImagePattern(img));
    }

    public static int generateOTP() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generate a random number between 100000 and 999999
    }

    @FXML
    void btnCloseOnMouseClicked(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            if (windowController != null) {
                windowController.close(stage);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error closing the window: " + e.getMessage());
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
            showAlert(Alert.AlertType.ERROR, "Error minimizing the window: " + e.getMessage());
        }
    }

    @FXML
    void OnMousePressed(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error handling mouse press: " + e.getMessage());
        }
    }

    @FXML
    void OnMouseDragged(MouseEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error handling mouse drag: " + e.getMessage());
        }
    }

    @FXML
    void btnChangePassOnAction(ActionEvent event) {
        String userId = txtFieldUserid.getText();
        String otp = txtOtpCode.getText();
        String pass = txtFieldPassword.getText();
        String confirmPass = txtFieldConfirmPassword.getText();

        if (userId != null && !userId.isEmpty() && otp != null && !otp.isEmpty() && pass != null && !pass.isEmpty() && confirmPass != null && !confirmPass.isEmpty()) {
            if (isValid()) {
                try {
                    if (forgotPasswordBO.isUserExist(userId)) {
                        if (confirmPass.equals(pass)) {
                            if (Integer.parseInt(otp) == otpCode) {
                                try {
                                    String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt());
                                    boolean isUpdated = forgotPasswordBO.changePassword(userId, hashedPassword);
                                    if (isUpdated) {
                                        showAlert(Alert.AlertType.CONFIRMATION, "Password Changed!");
                                        otpCode = -1;
                                        clearFields();
                                    } else {
                                        showAlert(Alert.AlertType.WARNING, "Something went wrong!");
                                    }
                                } catch (SQLException e) {
                                    showAlert(Alert.AlertType.ERROR, "Error updating password: " + e.getMessage());
                                }
                            } else {
                                showAlert(Alert.AlertType.ERROR, "OTP not matched!");
                                txtOtpCode.requestFocus();
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Passwords do not match!");
                            clearPasswordFields();
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "User not found!");
                        txtFieldUserid.requestFocus();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    showAlert(Alert.AlertType.ERROR, "Error while checking user: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Incorrect value in fields!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Enter all mandatory details!");
        }
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.cas/Login_page.fxml"));
        Stage stage = new Stage();

        StackPane loginRoot = null;
        try {
            loginRoot = loader.load();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading login page: " + e.getMessage());
            return;
        }

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
        stage.centerOnScreen();

        Stage currentStage = (Stage) rootNode.getScene().getWindow();
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(10), currentStage.getScene().getRoot());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished(e -> {
            currentStage.hide();
            stage.show();

            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(10), stage.getScene().getRoot());
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        });
        fadeOutTransition.play();
    }

    @FXML
    void btnSendOtpOnAction(ActionEvent event) {
        otpCode = generateOTP();
        String userId = txtFieldUserid.getText();

        if (userId != null && !userId.isEmpty()) {
            try {
                if (forgotPasswordBO.isUserExist(userId)) {
                    UserDTO userDTO = forgotPasswordBO.searchUserById(userId);

                    String emailTitle = "OTP Code";
                    String emailContent = "Your One-Time OTP Code: " + otpCode;

                    EmailSender emailSender = new EmailSender();
                    boolean isSent = emailSender.sendEmail(userDTO.getEmail(), emailTitle, emailContent);

                    if (isSent) {
                        showAlert(Alert.AlertType.CONFIRMATION, "OTP sent to your email successfully!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Failed to send OTP!");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "User not found!");
                    txtFieldUserid.requestFocus();
                }
            } catch (SQLException | ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error while processing OTP: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Enter a User ID!");
            txtFieldUserid.requestFocus();
        }
    }

    public boolean isValid() {
        return Regex.setTextColor(TextField.OTP, txtOtpCode);
    }

    @FXML
    void txtOtpCodeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.OTP, txtOtpCode);
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }

    private void clearFields() {
        txtOtpCode.setText("");
        txtFieldUserid.setText("");
        txtFieldPassword.setText("");
        txtFieldConfirmPassword.setText("");
        txtFieldUserid.requestFocus();
    }

    private void clearPasswordFields() {
        txtFieldPassword.setText("");
        txtFieldConfirmPassword.setText("");
        txtFieldPassword.requestFocus();
    }
}
