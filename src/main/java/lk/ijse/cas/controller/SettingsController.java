package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.SettingsBO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.util.AlertHandler;
import lk.ijse.cas.util.Regex;
import lk.ijse.cas.util.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class SettingsController {

    @FXML
    private JFXComboBox<String> cmbPosition;

    @FXML
    private JFXTextField txtFieldEmail;

    @FXML
    private JFXTextField txtFieldUserId;

    @FXML
    private JFXTextField txtFieldUserIdUd;

    @FXML
    private JFXTextField txtFieldUserName;

    @FXML
    private JFXTextField txtFieldUserNameUd;

    @FXML
    private MFXPasswordField txtFieldConfirmPassword;

    @FXML
    private MFXPasswordField txtFieldConfirmPasswordUd;

    @FXML
    private MFXPasswordField txtFieldNewPassword;

    @FXML
    private MFXPasswordField txtFieldNewPasswordUd;

    @FXML
    private Group registorGroup;

    private UserDTO user;
    private SettingsBO settingsBO = (SettingsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SETTINGS);

    public void initialize() {
        initializeCmb();
    }

    private void initializeTextFields() {
        if (user != null) {
            txtFieldUserIdUd.setText(user.getUserId());
            txtFieldUserNameUd.setText(user.getUserName());
        }
    }

    private void initializeCmb() {
        cmbPosition.getItems().addAll("Admin", "Coordinator");
    }

    public void setUser(UserDTO user) {
        this.user = user;
        initializeTextFields();
        setButtonVisibilityBasedOnRole();
    }

    private void setButtonVisibilityBasedOnRole() {
        try {
            UserDTO userDTO = settingsBO.getRole(user.getUserId());
            String role = userDTO.getPosition();
            if ("Coordinator".equals(role)) {
                setRegistorVisibility(false);
            }
        } catch (SQLException | ClassNotFoundException e) {
            AlertHandler.showError("Error loading user role: " + e.getMessage());
        }
    }

    private void setRegistorVisibility(boolean visible) {
        registorGroup.setVisible(visible);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String userId = txtFieldUserIdUd.getText();
        String userName = txtFieldUserNameUd.getText();
        String newPassword = txtFieldNewPasswordUd.getText();
        String confirmPassword = txtFieldConfirmPasswordUd.getText();

        if (isInputValidForUpdate(userId, userName, newPassword, confirmPassword)) {
            if (newPassword.equals(confirmPassword)) {
                if (user.getUserId().equals(userId)) {
                    updateUserDetails(userId, userName, newPassword);
                } else {
                    AlertHandler.showError("User ID mismatch: The user ID already exists.");
                }
            } else {
                AlertHandler.showError("Password mismatch: Passwords do not match.");
            }
        } else {
            AlertHandler.showWarning("Missing Details: Please fill in all the mandatory fields.");
        }
    }

    private boolean isInputValidForUpdate(String userId, String userName, String newPassword, String confirmPassword) {
        return !(userId == null || userId.isEmpty() || userName == null || userName.isEmpty() ||
                newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty());
    }

    private void updateUserDetails(String userId, String userName, String newPassword) {
        try {
            UserDTO oldUser = settingsBO.searchUserById(userId);
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            UserDTO newUser = new UserDTO(userId, userName, oldUser.getPosition(), hashedPassword, oldUser.getEmail());

            boolean isUpdated = settingsBO.updateUser(newUser, user.getUserId());
            if (isUpdated) {
                AlertHandler.showInfo("User details updated successfully!");
                clearPasswordFields();
            } else {
                AlertHandler.showWarning("Update failed: Something went wrong while updating the user details.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            AlertHandler.showError("Error updating user details: " + e.getMessage());
        }
    }

    private void clearPasswordFields() {
        txtFieldNewPasswordUd.clear();
        txtFieldConfirmPasswordUd.clear();
    }

    @FXML
    void btnRegistorOnAction(ActionEvent event) {
        String userId = txtFieldUserId.getText();
        String username = txtFieldUserName.getText();
        String position = cmbPosition.getValue();
        String email = txtFieldEmail.getText();
        String password = txtFieldNewPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();

        if (isInputValidForRegistration(userId, username, email, password, confirmPassword)) {
            if (isValidInput()) {
                registerUser(userId, username, position, email, password, confirmPassword);
            } else {
                AlertHandler.showError("Invalid Input: Please check the entered values.");
            }
        } else {
            AlertHandler.showWarning("Missing Details: Please fill in all the mandatory fields.");
        }
    }

    private boolean isInputValidForRegistration(String userId, String username, String email, String password, String confirmPassword) {
        return !(userId == null || userId.isEmpty() || username == null || username.isEmpty() ||
                email == null || email.isEmpty() || password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty());
    }

    private boolean isValidInput() {
        return Regex.setTextColor(TextField.NAME, txtFieldUserName) && Regex.setTextColor(TextField.EMAIL, txtFieldEmail);
    }

    private void registerUser(String userId, String username, String position, String email, String password, String confirmPassword) {
        try {
            if (settingsBO.isUserAvailable(userId)) {
                if (password.equals(confirmPassword)) {
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    UserDTO user = new UserDTO(userId, username, position, hashedPassword, email);

                    boolean isSaved = settingsBO.registor(user);
                    if (isSaved) {
                        AlertHandler.showInfo("User registered successfully!");
                        clearRegistrationFields();
                    } else {
                        AlertHandler.showWarning("Registration failed: Something went wrong during registration.");
                    }
                } else {
                    AlertHandler.showError("Password mismatch: Passwords do not match.");
                    clearPasswordFields();
                }
            } else {
                AlertHandler.showError("User ID exists: The user ID already exists.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            AlertHandler.showError("Registration Error: " + e.getMessage());
        }
    }

    private void clearRegistrationFields() {
        txtFieldUserId.clear();
        txtFieldUserName.clear();
        cmbPosition.setValue(null);
        txtFieldEmail.clear();
        txtFieldNewPassword.clear();
        txtFieldConfirmPassword.clear();
    }

    @FXML
    void txtFieldUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME, txtFieldUserName);
    }

    @FXML
    void txtFieldEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.EMAIL, txtFieldEmail);
    }

    @FXML
    void txtFieldUserNameUdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME, txtFieldUserNameUd);
    }
}
