package lk.ijse.cas.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cas.bo.BOFactory;
import lk.ijse.cas.bo.custom.CourseBO;
import lk.ijse.cas.bo.custom.PaymentBO;
import lk.ijse.cas.dto.CourseDTO;
import lk.ijse.cas.dto.PaymentDTO;
import lk.ijse.cas.dto.StudentDTO;
import lk.ijse.cas.view.tdm.CoursePriceTm;
import lk.ijse.cas.view.tdm.PaymentTm;
import lk.ijse.cas.util.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentController {

    @FXML
    private JFXComboBox<String> cmbCourses;

    @FXML
    private JFXComboBox<String> cmbPMethod;

    @FXML
    private JFXComboBox<String> cmbPType;

    @FXML
    private TableColumn<?, ?> columnAmount;

    @FXML
    private TableColumn<?, ?> columnCourse;

    @FXML
    private TableColumn<?, ?> columnCourses;

    @FXML
    private TableColumn<?, ?> columnDate;

    @FXML
    private TableColumn<?, ?> columnPId;

    @FXML
    private TableColumn<?, ?> columnPMethod;

    @FXML
    private TableColumn<?, ?> columnPType;

    @FXML
    private TableColumn<?, ?> columnPrice;

    @FXML
    private TableColumn<?, ?> columnSId;

    @FXML
    private TableColumn<?, ?> columnSName;

    @FXML
    private TableColumn<?, ?> columnUpfront;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDue;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane paymentsPane;

    @FXML
    private TableView<CoursePriceTm> tableCourses;

    @FXML
    private TableView<PaymentTm> tablePayment;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtStudentId;

    @FXML
    private JFXTextField txtUAmount;

    private List<PaymentDTO> paymentList;

    private ObservableList<PaymentTm> ptmList;

    private ObservableList<String> selectedCourses;

    private ObservableList<CoursePriceTm> coursesData;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURSE);



    public void initialize() {

        initializePaymentId();
        initializeDate();
        initializeTotal();
        initializeCmbAndCb();
        initializeCourses();

        this.paymentList = getAllPayments();
        setCellValueFactory();
        loadPaymentTable();
    }

    public static String convertListToString(ObservableList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(", ");
        }
        // Remove the trailing ", " if the list is not empty
        if (!list.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    private void loadPaymentTable(){
        ptmList = FXCollections.observableArrayList();

        for (PaymentDTO payment : paymentList) {

            ObservableList<String> courseList = FXCollections.observableArrayList();

            try {
                for (CourseDTO courseDTO : courseBO.getAllCourses()){
                    CoursePriceTm coursePriceTm = new CoursePriceTm(courseDTO.getName(), Double.parseDouble(courseDTO.getPrice()));
                    courseList.add(coursePriceTm.getCourse());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            PaymentTm paymentTm = null;

            try {
                paymentTm  = new PaymentTm(
                        payment.getPId(),
                        payment.getDate(),
                        payment.getSId(),
                        paymentBO.getStName(payment.getSId()),
                        convertListToString(courseList),
                        payment.getUpfrontP(),
                        payment.getTotalP(),
                        payment.getMethod(),
                        payment.getType()
                );
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            ptmList.add(paymentTm);
        }
        tablePayment.setItems(ptmList);
        tablePayment.refresh();
    }

    private void setCellValueFactory() {
        columnPId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnPMethod.setCellValueFactory(new PropertyValueFactory<>("pMethod"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("totalP"));
        columnSId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        columnSName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        columnCourses.setCellValueFactory(new PropertyValueFactory<>("courses"));
        columnUpfront.setCellValueFactory(new PropertyValueFactory<>("upfrontP"));
        columnPType.setCellValueFactory(new PropertyValueFactory<>("pType"));
    }

    private List<PaymentDTO> getAllPayments() {
        List<PaymentDTO> list = null;
        try {
            list = paymentBO.getAllPayments();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private void refreshTableView() {
        this.paymentList = getAllPayments();
        loadPaymentTable();
    }

    private void initializeTotal() {
        lblTotal.setText("0.0");
    }

    private void initializeCourses() {
        selectedCourses = FXCollections.observableArrayList();
        coursesData = FXCollections.observableArrayList();

        columnCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void initializeCmbAndCb() {
        cmbPMethod.getItems().addAll("Cash", "Card");
        cmbPType.getItems().addAll("Upfront", "Full");

        try {
            cmbCourses.setItems(paymentBO.getCourses());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        lblDate.setText(formattedDate);
    }

    private void initializePaymentId() {
        try {
            lblPaymentId.setText(paymentBO.getNextPaymentId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private double calculateTotal(ObservableList<CoursePriceTm> list) {
        double total = 0;
        for (CoursePriceTm cp : list) {
            total += cp.getPrice();
        }
        return total;
    }

    @FXML
    void btnAddCourseOnAction(ActionEvent event) {
        String selectedCourse = cmbCourses.getValue();
        if (selectedCourse != null) {

            // Check if the selected course already exists in the coursesData list
            boolean courseExists = coursesData.stream()
                    .anyMatch(cp -> cp.getCourse().equals(selectedCourse));

            if (!courseExists) {
                selectedCourses.add(selectedCourse);

                // Fetch the price for the selected course from your data source
                double price = 0;
                try {
                    price = paymentBO.getPrice(selectedCourse);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                CoursePriceTm cp = new CoursePriceTm(selectedCourse, price);
                coursesData.add(cp);

                // Calculate and update the total
                double total = calculateTotal(coursesData);
                lblTotal.setText(String.valueOf(total));

                tableCourses.setItems(coursesData);
            } else {
                new Alert(Alert.AlertType.WARNING, "Course already exists!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please select a course!").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        initializePaymentId();
        initializeDate();
        initializeTotal();

        txtStudentId.setText("");
        txtUAmount.setText("");

        cmbCourses.setValue(null);
        cmbPMethod.setValue(null);
        cmbPType.setValue(null);

        coursesData.clear();
        tableCourses.setItems(coursesData);
    }

    @FXML
    void btnCheckOutOnAction(ActionEvent event) {
        String paymnetId = lblPaymentId.getText();
        String pMethod = cmbPMethod.getValue();
        String pType = cmbPType.getValue();
        String studentId = txtStudentId.getText();
        String uAmount = txtUAmount.getText();
        String amount = lblTotal.getText();
        String date = null;

        LocalDate dateRaw = LocalDate.parse(lblDate.getText());

        if (dateRaw != null) {
            // Format the date as a SQL DATE string (yyyy-MM-dd)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = dateRaw.format(formatter);
            // Now you can use the sqlDate string as needed
        }

        PaymentDTO payment = new PaymentDTO(paymnetId, date, pMethod, pType, uAmount, amount, studentId, coursesData);

        if(paymnetId != null && !paymnetId.isEmpty() && pMethod != null && !pMethod.isEmpty() && pType != null && !pType.isEmpty() && studentId != null && !studentId.isEmpty() && uAmount != null && !uAmount.isEmpty()
                && date != null && !date.isEmpty() && coursesData != null && !coursesData.isEmpty()){
            if(Regex.setTextColor(lk.ijse.cas.util.TextField.ID, txtStudentId)){
                try {
                    if(paymentBO.isStudentExist(studentId)){
                        try {
                            boolean isSaved = paymentBO.checkOut(payment);
                            if(isSaved){
                                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
                                refreshTableView();
                                sendPaymentConfirmationEmail(paymnetId);
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Student not found").show();
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

    private void sendPaymentConfirmationEmail(String paymnetId) throws SQLException, ClassNotFoundException {
        PaymentDTO payment = paymentBO.searchByPaymentId(paymnetId);
        StudentDTO student = paymentBO.searchByStudentId(payment.getSId());

        if(student.getEmail() != null  && !student.getEmail().isEmpty()){
            ObservableList<String> list = FXCollections.observableArrayList();
            for (CoursePriceTm cp : payment.getCp()){
                list.add(cp.getCourse());
            }

            String emailTitle = "Payment Confirmation - Your Enrollment at Sahan Learner's";

            String emailContent = "Dear " + student.getFname() + ",\n\n" +
                    "We're thrilled to confirm that your payment for enrollment at Sahan Learner's has been successfully processed! This email provides a summary of your registration details.\n\n" +

                    "Student Information:\n" +
                    "* Full Name: " + student.getFname() + " " + student.getLname() + "\n" +
                    "* NIC: " + student.getNIC() + "\n\n" +  // Replace with actual student email address

                    "Payment Details:\n" +
                    "* Payment ID: " + paymnetId + "\n" +
                    "* Date: " + payment.getDate() + "\n" +
                    "* Payment Method: " + payment.getMethod() + "\n" +
                    "* Description: " + payment.getDesc() + "\n\n" +

                    "Enrollment Details:\n" +
                    "* Courses Enrolled:\n";

            for (String courseName : list) {
                emailContent += "    * " + courseName + "\n";
            }

            emailContent += "* Total Amount: " + payment.getAmount() + "\n\n" +

                    "Next Steps:\n" +
                    "* With your successful enrollment, you'll soon receive a separate email with access instructions for your chosen courses. This email will include login credentials and any additional information needed to begin learning.\n" +
                    "* In the meantime, if you have any questions regarding your enrollment or the courses." +
                    "* Our friendly support team is also available to assist you. You can reach them at 'sahanlearnersofficial@gmail.com' or by phone at 076677409 / 0742634670 (if applicable).\n\n" +

                    "Thank you for choosing Sahan Learner's! We're excited to have you on board and look forward to your learning journey with us.\n\n" +
                    "Best regards,\n" +
                    "Sahan Learner's";

            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(student.getEmail(), emailTitle, emailContent);
        } else {
            new Alert(Alert.AlertType.ERROR, "Email not found!").show();
        }
    }

    @FXML
    void btnRemoveCourseOnAction(ActionEvent event) {
        CoursePriceTm selectedCoursePrice = tableCourses.getSelectionModel().getSelectedItem();
        if (selectedCoursePrice != null) {
            coursesData.remove(selectedCoursePrice);
            selectedCourses.remove(selectedCoursePrice.getCourse());

            // Calculate and update the total
            double total = calculateTotal(coursesData);
            lblTotal.setText(String.valueOf(total));

            tableCourses.setItems(coursesData);


        } else {
            new Alert(Alert.AlertType.INFORMATION, "Select a course first!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String paymnetId = lblPaymentId.getText();
        String desc = txtDescription.getText();
        String pMethod = cmbPMethod.getValue();
        String studentId = txtStudentId.getText();
        String amount = lblTotal.getText();
        String date = null;

        LocalDate dateRaw = LocalDate.parse(lblDate.getText());

        if (dateRaw != null) {
            // Format the date as a SQL DATE string (yyyy-MM-dd)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = dateRaw.format(formatter);
            // Now you can use the sqlDate string as needed
        }

        // Get the existing Payment object from the search
        PaymentDTO payment = null;
        try {
            payment = paymentBO.searchByPaymentId(paymnetId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return;
        }

        // Update the fields of the existing Payment object
        try {
            if (payment != null && paymentBO.isPaymentExist(paymnetId)) {
                payment.setDesc(desc);
                payment.setMethod(pMethod);
                payment.setAmount(amount);
                payment.setSId(studentId);
                payment.setDate(date);
                payment.getCp().clear(); // Clear the existing course list
                payment.getCp().addAll(coursesData); // Add the updated course list

                if (paymnetId != null && !paymnetId.isEmpty() && pMethod != null && !pMethod.isEmpty() && studentId != null && !studentId.isEmpty()
                        && date != null && !date.isEmpty() && coursesData != null && !coursesData.isEmpty()) {
                    if(Regex.setTextColor(lk.ijse.cas.util.TextField.ID, txtStudentId)){
                        if(paymentBO.isStudentExist(studentId)){
                            try {
                                boolean isUpdated = paymentBO.update(payment);
                                if (isUpdated) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
                                    refreshTableView();
                                    clearFields();
                                }
                            } catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Student not found").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Incorrect value in fields!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Enter all mandatory details!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        clearFields();
        lblPaymentId.setText(id);

        if (id != null && !id.isEmpty()){
            if(Regex.setTextColor(lk.ijse.cas.util.TextField.ID, txtSearch)){
                try {
                    PaymentDTO payment = paymentBO.searchByPaymentId(id);

                    if (payment != null){
                        lblPaymentId.setText(payment.getPId());
                        lblDate.setText(payment.getDate());
                        txtDescription.setText(payment.getDesc());
                        cmbPMethod.setValue(payment.getMethod());
                        txtStudentId.setText(payment.getSId());

                        // Update coursesData list
                        coursesData.clear(); // Clear existing courses

                        // Add courses from payment
                        for (CoursePriceTm coursePriceTm : payment.getCp()){
                            coursesData.add(coursePriceTm);
                        }

                        double total = calculateTotal(coursesData);
                        lblTotal.setText(String.valueOf(total));

                        tableCourses.setItems(coursesData); // Update table view

                    }else {
                        new Alert(Alert.AlertType.ERROR, "Payment not Found!").show();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect value in fields!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter a Payment Id!").show();
        }
    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.cas.util.TextField.ID, txtSearch);
    }

    @FXML
    void txtStudentIdOnKeyRelesed(KeyEvent event) {
        Regex.setTextColor(lk.ijse.cas.util.TextField.ID, txtStudentId);
    }

    public void txtUAmountOnKeyRelesed(KeyEvent keyEvent) {

    }
}
