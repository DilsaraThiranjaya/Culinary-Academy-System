package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
        txtFieldUserId.setText(user.getUserId());
        txtFieldUserName.setText(user.getUserName());
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
            if(Regex.setTextColor(TextField.NAME,txtFieldUserName)){
                try {
                    if (user.getUserId().equals(userId)){
                        UserDTO oldUser = settingsBO.searchUserById(userId);
                        if(confirmPassword.equals(newPassword)) {
                            UserDTO newUser = new UserDTO(userId, userName, oldUser.getPosition(), newPassword, oldUser.getEmail());
                            try {
                                boolean isUpdated = settingsBO.updateUser(newUser,user.getUserId());
                                if (isUpdated) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "User details updated!").show();
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
                    } else {
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

    @FXML
    void btnRegistorOnAction(ActionEvent event) {
        String userId = txtFieldUserId.getText();
        String username = txtFieldUserName.getText();
        String position = cmbPosition.getValue();
        String email = txtFieldEmail.getText();
        String password = txtFieldNewPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();

        if(userId != null && !userId.isEmpty() && username != null && !username.isEmpty() && employeeId != null && !employeeId.isEmpty() && password != null && !password.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty() && otp != null && !otp.isEmpty()){
            if(isValid()){
                try {
                    if (registorBO.isUserAvailable(userId)){
                        if(registorBO.isEmployeeAvailable(employeeId)){
                            UserDTO user = new UserDTO(userId, username, password, employeeId);

                            if(confirmPassword.equals(password)) {
                                if(Integer.parseInt(otp) == otpCode){
                                    try {
                                        boolean isSaved = registorBO.registor(user);
                                        if (isSaved) {
                                            new Alert(Alert.AlertType.CONFIRMATION, "New user registored!").show();
                                            otpCode = -1;
                                            txtOtpCode.setText("");
                                            txtFieldUserid.setText("");
                                            txtFieldUsername.setText("");
                                            cmbEmployees.setValue("");
                                            txtFieldPassword.setText("");
                                            txtFieldConfirmPassword.setText("");
                                            txtFieldUserid.requestFocus();
                                        }else {
                                            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
                                        }
                                    } catch (SQLException e) {
                                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                    }
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "OTP not matched!").show();
                                    txtOtpCode.requestFocus();
                                }
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Passwors is not matched!").show();
                                txtFieldPassword.setText("");
                                txtFieldConfirmPassword.setText("");
                                txtFieldPassword.requestFocus();
                            }
                        }else {
                            new Alert(Alert.AlertType.ERROR, "User with this Employee ID already exist!").show();
                            cmbEmployees.setValue("");
                            cmbEmployees.requestFocus();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR, "User ID already exist!").show();
                        txtFieldUserid.requestFocus();
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
    void txtFieldUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME,txtFieldUserName);
    }

    @FXML
    void txtFieldEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtFieldUserIdOnKeyReleased(KeyEvent event) {

    }

}
