package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.DashboardBO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.util.WindowController;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DashboardController {

    @FXML
    private JFXButton btnSettings;

    @FXML
    private HBox iconSettings;

    @FXML
    private JFXButton btnPayments;

    @FXML
    private JFXButton btnStudents;

    @FXML
    private JFXButton btnCourses;

    @FXML
    private HBox iconCourses;

    @FXML
    private HBox iconPayments;

    @FXML
    private HBox iconStudents;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Label lblUiUsername;

    @FXML
    private ImageView ivLogoutIcon;

    @FXML
    private ImageView ivRegistorIcon;

    @FXML
    private Label lblLogout;

    @FXML
    private Label lblRegistor;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane taskBarPane;

    private HBox selectedIcon;

    private JFXButton selectedButton;

    private UserDTO user;

    private WindowController windowController;

    private double xOffset = 0;
    private double yOffset = 0;

    DashboardBO dashboardBO = (DashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DASHBOARD);



    public void initialize() {
        sidebarButtonHoverUtilize();
    }

    public void disableSidebarButtons() {
        btnPayments.setVisible(false);
        iconPayments.setVisible(false);
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }


    public void setUser(UserDTO user) {
        this.user = user;
        setUiUsername();
        setDisabledButton();
    }

    private void setDisabledButton() {
        String role = null;
        try {
            role = dashboardBO.getRole(user.getEmployeeId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(role != null && role.equals("Instructor")){
            disableSidebarButtons();
            loadPage("Vehicle_page");
            setDefaultSelectedButtonStyle(role);
        } else {
            loadPage("Employee_page");
            setDefaultSelectedButtonStyle(role);
        }
    }

    private void setUiUsername() {
        if (user != null) {
            lblUiUsername.setText(user.getUserName());
        }
    }

    private void sidebarButtonHoverUtilize() {
        addHoverEffect(btnStudents,iconStudents);
        addHoverEffect(btnPayments,iconPayments);
        addHoverEffect(btnCourses,iconCourses);
        addHoverEffect(btnSettings,iconSettings);
        addHoverEffect(lblRegistor,ivRegistorIcon, "/lk.ijse.cas/asserts/icons/hover/Registor_icon_hover.png", "/lk.ijse.cas/asserts/icons/Registor_icon.png");
        addHoverEffect(lblLogout,ivLogoutIcon, "/lk.ijse.cas/asserts/icons/hover/Logout_icon_hover.png", "/lk.ijse.cas/asserts/icons/Logout_icon.png");
    }

    public void addHoverEffect(Label button, ImageView icon, String hoverPath, String defaultPath) {
        button.setOnMouseEntered(event -> {
                button.setStyle("-fx-text-fill: #6B240C; -fx-border-color: #6B240C; -fx-border-width: 0px 0px 2px 0px;");

                Image hoverIcon = new Image(hoverPath);
                icon.setImage(hoverIcon);
        });

        button.setOnMouseExited(event -> {
                button.setStyle("-fx-text-fill: White;");

                Image defaultIcon = new Image(defaultPath);
                icon.setImage(defaultIcon);
        });
    }

    public void addHoverEffect(JFXButton button, HBox icon) {
        button.setOnMouseEntered(event -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color:  #E48F45a1; -fx-background-radius: 0px 30px 30px 0px; -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  #E48F45a1; -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
                icon.setStyle("-fx-background-color:  #E48F45a1; -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  #6B240C; -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
            }
        });

        button.setOnMouseExited(event -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;");
                icon.setStyle("-fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;");
            }
        });

        icon.setOnMouseEntered(event -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color:  #E48F45a1; -fx-background-radius: 0px 30px 30px 0px; -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  #E48F45a1; -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
                icon.setStyle("-fx-background-color:  #E48F45a1; -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  #6B240C; -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
            }
        });

        icon.setOnMouseExited(event -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;");
                icon.setStyle("-fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;");
            }
        });
    }

    private void setDefaultSelectedButtonStyle(String role) {
        if(role != null && role.equals("Instructor")){
            selectedButton = btnVehicles; // Set the initial selected button
            selectedIcon = iconVehicles;
            } else {
            selectedButton = btnEmployees; // Set the initial selected button
            selectedIcon = iconEmployees;
        }
        selectedButton.setStyle("-fx-background-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #994D1C 0.0%, #F5CCA0 100.0%); -fx-background-radius: 0px 30px 30px 0px; -fx-effect: dropshadow(gaussian, #F5CCA0, 10, 0, 0, 0); -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #994D1C 0.0%, #F5CCA0 100.0%); -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;"); // Apply the selected style
        selectedIcon.setStyle("-fx-background-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #6B240C 0.0%, #F5CCA0 100.0%); -fx-effect: dropshadow(gaussian, #F5CCA0, 10, 0, 0, 0); -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #6B240C 0.0%, #F5CCA0 100.0%); -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
    }

    private void handleSelection(JFXButton button, HBox icon) {
        if (selectedButton != null) {
            selectedButton.setStyle(" -fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;"); // Deselect the previously selected button

        }
        selectedButton = button; // Set the new selected button
        selectedButton.setStyle("-fx-background-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #994D1C 0.0%, #F5CCA0 100.0%); -fx-background-radius: 0px 30px 30px 0px; -fx-effect: dropshadow(gaussian, #F5CCA0, 10, 0, 0, 0); -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #994D1C 0.0%, #F5CCA0 100.0%); -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;"); // Apply the selected style

        if (selectedIcon != null) {
            selectedIcon.setStyle(" -fx-background-color:  transparent; -fx-background-radius: 0px 0px 0px 0px; -fx-border-width: 0px 0px 0px 0px;");

        }
        selectedIcon = icon;
        selectedIcon.setStyle("-fx-background-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #6B240C 0.0%, #F5CCA0 100.0%); -fx-effect: dropshadow(gaussian, #F5CCA0, 10, 0, 0, 0); -fx-transition: background-color 0.3s, scale-x 0.3s, scale-y 0.3s; -fx-border-color:  linear-gradient(from 53.5545% 61.6114% to 53.5545% 100.0%, #6B240C 0.0%, #F5CCA0 100.0%); -fx-border-width: 0px 0px 0px 3px; -fx-border-radius: 5px;");
    }

    public void loadPage(String page) {
        AnchorPane root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.cas/" + page + ".fxml"));
            root = loader.load();

            if (page.equals("Setting_page")) {
                SettingsController settingsController = loader.getController();
                settingsController.setUser(user);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(root);

    }

    @FXML
    void btnLogoutOnClicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/lk.ijse.cas/Login_page.fxml"));
        StackPane loginRoot = null;
        try {
            loginRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Rectangle clip = new Rectangle(958, 622);
        clip.setArcHeight(90);
        clip.setArcWidth(90);

        loginRoot.setClip(clip);

        LoginController loginController = loader.getController();

        loginController.setWindowController(windowController);

        Scene scene = new Scene(loginRoot);
        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage();

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Loging_page");
        stage.centerOnScreen();


        // Get the current stage and close it when the new stage is shown
        Stage currentStage = (Stage) rootNode.getScene().getWindow();
        stage.setOnShown( e -> currentStage.close());

        stage.show();
    }

    @FXML
    void btnRegistorOnClicked(MouseEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("/lk.ijse.cas/Register_page.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registor Form");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL); // Set as modal

        stage.showAndWait();
    }

    @FXML
    void btnCoursesOnClicked(MouseEvent event) {
        loadPage("Course_page");
        handleSelection(btnCourses,iconCourses);
    }

    @FXML
    void btnPaymentsOnClicked(MouseEvent event) {
        loadPage("Payment_page");
        handleSelection(btnPayments,iconPayments);
    }

    @FXML
    void btnStudentsOnClicked(MouseEvent event) {
        loadPage("Student_page");
        handleSelection(btnStudents,iconStudents);
    }

    @FXML
    void btnSettingsOnClicked(MouseEvent event) {
        loadPage("Setting_page");
        handleSelection(btnSettings,iconSettings);
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
