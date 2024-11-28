package lk.ijse.cas.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.PaymentDTO;
import lk.ijse.cas.dto.StudentDTO;
import lk.ijse.cas.view.tdm.CoursePriceTm;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public String getStName(String id) throws SQLException, ClassNotFoundException ;

    public List<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException ;

    public ObservableList<String> getCourses() throws SQLException, ClassNotFoundException ;

    public String getNextPaymentId() throws SQLException, ClassNotFoundException ;

    public double getPrice(String selectedCourse) throws SQLException, ClassNotFoundException ;

    public boolean isStudentExist(String studentId) throws SQLException, ClassNotFoundException ;

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean savePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean saveCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean checkOut(PaymentDTO paymentDTO) throws SQLException ;

    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean updatePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;

    public boolean searchAndDeletePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean updateCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException ;

    public boolean searchAndDeleteCourses(String sId) throws SQLException, ClassNotFoundException ;

    public boolean update(PaymentDTO paymentDTO) throws SQLException ;

    public PaymentDTO searchByPaymentId(String paymentId) throws SQLException, ClassNotFoundException ;

    public StudentDTO searchByStudentId(String studentId) throws SQLException, ClassNotFoundException ;

    public boolean isPaymentExist(String paymentId) throws SQLException, ClassNotFoundException ;

    public boolean isStudentPaymentExist(String studentId);

    public ObservableList<CoursePriceTm> getAllPaymentDetails(String pId) throws SQLException, ClassNotFoundException;
}
