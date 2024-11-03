package lk.ijse.cas.controller;

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
    private AnchorPane settingsPane;

    @FXML
    private JFXPasswordField txtFieldConfirmPassword;

    @FXML
    private JFXPasswordField txtFieldNewPassword;

    @FXML
    private JFXTextField txtFieldUserId;

    @FXML
    private JFXTextField txtFieldUserName;

    private UserDTO user;

    SettingsBO settingsBO = (SettingsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SETTINGS);


    private void initializeTextFeildText() {
        txtFieldUserId.setText(user.getUserId());
        txtFieldUserName.setText(user.getUserName());
    }

    public void setUser(UserDTO user) {
        this.user = user;
        initializeTextFeildText();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String userId = txtFieldUserId.getText();
        String userName = txtFieldUserName.getText();
        String newPassword = txtFieldNewPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();

        if(userId != null && !userId.isEmpty() && userName != null && !userName.isEmpty() && newPassword != null && !newPassword.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty()) {
            if(Regex.setTextColor(TextField.NAME,txtFieldUserName)){
                try {
                    if (user.getUserId().equals(userId) || settingsBO.isUserAvailable(userId)){
                        if(confirmPassword.equals(newPassword)) {
                            UserDTO newUser = new UserDTO(userId, userName, newPassword,user.getEmployeeId());
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
    void txtFieldUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextField.NAME,txtFieldUserName);
    }

}
