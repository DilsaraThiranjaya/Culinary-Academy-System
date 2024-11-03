package lk.ijse.cas.bo.custom.impl;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.cas.bo.custom.PaymentBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.*;
import lk.ijse.cas.db.DBConnection;
import lk.ijse.cas.dto.PaymentDTO;
import lk.ijse.cas.dto.StudentDTO;
import lk.ijse.cas.entity.Payment;
import lk.ijse.cas.entity.PaymentDetails;
import lk.ijse.cas.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);
    PaymentDetailsDAO paymentDetailsDAO = (PaymentDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT_DETAILS);
    CourseDetailsDAO courseDetailsDAO = (CourseDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE_DETAILS);


    @Override
    public String getStName(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.getStName(id);
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        List<Payment> paymentList = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payment payment : paymentList) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    payment.getPId(),
                    payment.getDesc(),
                    payment.getDate(),
                    payment.getMethod(),
                    payment.getAmount(),
                    payment.getSId(),
                    payment.getCp()
            );

            paymentDTOs.add(paymentDTO);
        }
        return paymentDTOs;
    }

    @Override
    public ObservableList<String> getCourses() throws SQLException, ClassNotFoundException {
        return courseDAO.getCourses();
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    @Override
    public double getPrice(String selectedCourse) throws SQLException, ClassNotFoundException {
        return courseDAO.getPrice(selectedCourse);
    }

    @Override
    public boolean isStudentExist(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.isExist(new Student(studentId, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                paymentDTO.getPId(),
                paymentDTO.getDesc(),
                paymentDTO.getDate(),
                paymentDTO.getMethod(),
                paymentDTO.getAmount(),
                paymentDTO.getSId(),
                null
        ));
    }

    @Override
    public boolean savePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDetailsDAO.save(new PaymentDetails(paymentDTO.getPId(), null), paymentDTO.getCp());
    }

    @Override
    public boolean saveCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.save(paymentDTO.getSId(), paymentDTO.getCp());
    }

    @Override
    public boolean checkOut(PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPaymentSaved = savePayment(paymentDTO);
            if (isPaymentSaved) {
                boolean isPaymentDetailsSaved = savePaymentDetails(paymentDTO);
                if (isPaymentDetailsSaved) {
                    boolean isCourseDetailsSaved = saveCourseDetails(paymentDTO);
                    if (isCourseDetailsSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(
                paymentDTO.getPId(),
                paymentDTO.getDesc(),
                paymentDTO.getDate(),
                paymentDTO.getMethod(),
                paymentDTO.getAmount(),
                paymentDTO.getSId(),
                null
        ));
    }

    @Override
    public boolean updatePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        boolean isDeleted = searchAndDeletePaymentDetails(paymentDTO);
        if(isDeleted){
            boolean isSaved = savePaymentDetails(paymentDTO);
            if(isSaved){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean searchAndDeletePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDetailsDAO.searchAndDeletePaymentDetails(new PaymentDetails(paymentDTO.getPId(), null));
    }

    @Override
    public boolean updateCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        boolean isDeleted = searchAndDeleteCourses(paymentDTO.getSId());
        if(isDeleted){
            boolean isSaved = saveCourseDetails(paymentDTO);
            if(isSaved){
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "SCD").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "SADC").show();
        }
        return false;
    }

    @Override
    public boolean searchAndDeleteCourses(String sId) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.searchAndDeleteCourses(sId);
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPaymentUpdated = updatePayment(paymentDTO);
            if (isPaymentUpdated) {
                boolean isPaymentDetailsUpdated = updatePaymentDetails(paymentDTO);
                if (isPaymentDetailsUpdated) {
                    boolean isCourseDetailsUpdated = updateCourseDetails(paymentDTO);
                    if (isCourseDetailsUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public PaymentDTO searchByPaymentId(String paymentId) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.searchById(new Payment(paymentId, null, null, null, null, null, null));
        return payment != null ? new PaymentDTO(
                payment.getPId(),
                payment.getDesc(),
                payment.getDate(),
                payment.getMethod(),
                payment.getAmount(),
                payment.getSId(),
                payment.getCp()
        ) : null;
    }

    @Override
    public StudentDTO searchByStudentId(String studentId) throws SQLException, ClassNotFoundException {
        Student student = studentDAO.searchById(new Student(studentId, null, null, null, null, null, null, null, null, null));

        return student != null ? new StudentDTO(
                student.getId(),
                student.getFname(),
                student.getLname(),
                student.getDOb(),
                student.getGender(),
                student.getAdmissionDate(),
                student.getNIC(),
                student.getAddress(),
                student.getCNo(),
                student.getEmail()
        ) : null;
    }

    @Override
    public boolean isPaymentExist(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.isExist(new Payment(paymentId, null, null, null, null, null, null));
    }
}
