package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.SettingsBO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.util.Regex;
import lk.ijse.cas.util.TextField;

import java.sql.SQLException;

public class SettingsController {

    @FXML
    private JFXComboBox<String> cmbPosition;

    @FXML
    private JFXPasswordField txtFieldConfirmPassword;

    @FXML
    private JFXPasswordField txtFieldConfirmPasswordUd;

    @FXML
    private JFXTextField txtFieldEmail;

    @FXML
    private JFXPasswordField txtFieldNewPassword;

    @FXML
    private JFXPasswordField txtFieldNewPasswordUd;

    @FXML
    private JFXTextField txtFieldUserId;

    @FXML
    private JFXTextField txtFieldUserIdUd;

    @FXML
    private JFXTextField txtFieldUserName;

    @FXML
    private JFXTextField txtFieldUserNameUd;

    private UserDTO user;

    SettingsBO settingsBO = (SettingsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SETTINGS);


    public void initialize() {
        initializeCmb();
    }

    private void initializeTextFeildText() {
        txtFieldUserIdUd.setText(user.getUserId());
        txtFieldUserNameUd.setText(user.getUserName());
    }

    private void initializeCmb() {
        cmbPosition.getItems().addAll("Admin", "Coordinator");
    }

    public void setUser(UserDTO user) {
        this.user = user;
        initializeTextFeildText();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String userId = txtFieldUserIdUd.getText();
        String userName = txtFieldUserNameUd.getText();
        String newPassword = txtFieldNewPasswordUd.getText();
        String confirmPassword = txtFieldConfirmPasswordUd.getText();

        if(userId != null && !userId.isEmpty() && userName != null && !userName.isEmpty() && newPassword != null && !newPassword.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty()) {
            if(Regex.setTextColor(TextField.NAME,txtFieldUserNameUd)){
                try {
                    if (user.getUserId().equals(userId)){
                        UserDTO oldUser = settingsBO.searchUserById(userId);
                        if(confirmPassword.equals(newPassword)) {
                            UserDTO newUser = new UserDTO(userId, userName, oldUser.getPosition(), newPassword, oldUser.getEmail());
                            try {
                                boolean isUpdated = settingsBO.updateUser(newUser,user.getUserId());
                                if (isUpdated) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "User details updated!").show();
                                    txtFieldNewPasswordUd.setText("");
                                    txtFieldConfirmPasswordUd.setText("");
                                    txtFieldUserIdUd.requestFocus();
                                }else {
                                    new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
                                }
                            } catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Passwors is not matched!").show();
                            txtFieldNewPasswordUd.setText("");
                            txtFieldConfirmPasswordUd.setText("");
                            txtFieldNewPasswordUd.requestFocus();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "User ID already exist!").show();
                        txtFieldUserIdUd.requestFocus();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect value in fields!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter all mandatory details!").show();
        }
    }

    @FXML
    void btnRegistorOnAction(ActionEvent event) {
        String userId = txtFieldUserId.getText();
        String username = txtFieldUserName.getText();
        String position = cmbPosition.getValue();
        String email = txtFieldEmail.getText();
        String password = txtFieldNewPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();

        if(userId != null && !userId.isEmpty() && username != null && !username.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty()){
            if(isValid()){
                try {
                    if (settingsBO.isUserAvailable(userId)){
                        UserDTO user = new UserDTO(userId, username, position, password, email);

                        if(confirmPassword.equals(password)) {
                            try {
                                boolean isSaved = settingsBO.registor(user);
                                if (isSaved) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "New user registored!").show();
                                    txtFieldUserId.setText("");
                                    txtFieldUserName.setText("");
                                    cmbPosition.setValue("");
                                    txtFieldEmail.setText("");
                                    txtFieldNewPassword.setText("");
                                    txtFieldConfirmPassword.setText("");
                                    txtFieldUserId.requestFocus();
                                }else {
                                    new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
                                }
                            } catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Passwors is not matched!").show();
                            txtFieldNewPassword.setText("");
                            txtFieldConfirmPassword.setText("");
                            txtFieldNewPassword.requestFocus();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR, "User ID already exist!").show();
                        txtFieldUserId.requestFocus();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect value in fields!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter all mandatory details!").show();
        }
    }

    public boolean isValid(){
        if (!Regex.setTextColor(TextField.NAME,txtFieldUserName)) return false;
        if (!Regex.setTextColor(TextField.EMAIL,txtFieldEmail)) return false;
        return true;
    }

    @FXML
    void txtFieldUserNameUdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME,txtFieldUserNameUd);
    }

    @FXML
    void txtFieldEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.EMAIL,txtFieldEmail);
    }

    @FXML
    void txtFieldUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME,txtFieldUserName);
    }

}
