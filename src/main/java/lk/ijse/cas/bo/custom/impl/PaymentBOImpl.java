package lk.ijse.cas.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.cas.bo.custom.PaymentBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.*;
import lk.ijse.cas.dto.PaymentDTO;
import lk.ijse.cas.dto.StudentDTO;
import lk.ijse.cas.embedded.PaymentDetailsPK;
import lk.ijse.cas.entity.Payment;
import lk.ijse.cas.entity.PaymentDetails;
import lk.ijse.cas.entity.Student;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            paymentDTOs.add(payment.toDTO());
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
        return studentDAO.isExist(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(paymentDTO.toEntity());
    }

    @Override
    public boolean savePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDetailsDAO.save(new PaymentDetails(new PaymentDetailsPK(paymentDTO.getPId(), null), null, null), paymentDTO.getCp());
    }

    @Override
    public boolean saveCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.save(paymentDTO.getSId(), paymentDTO.getCp());
    }

    @Override
    public boolean checkOut(PaymentDTO paymentDTO) {
        Transaction transaction = null;

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();  // Start transaction

            // Save payment
            if (savePayment(paymentDTO)) {
                // Save payment details
                if (savePaymentDetails(paymentDTO)) {
                    // Save course details
                    if (saveCourseDetails(paymentDTO)) {
                        transaction.commit();  // Commit the transaction
                        return true;
                    }
                }
            }

            if (transaction != null) {
                transaction.rollback();  // Rollback on failure
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback in case of an exception
            }
            e.printStackTrace();
            throw new RuntimeException("Transaction failed", e);
        }
    }



    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(paymentDTO.toEntity());
    }

    @Override
    public boolean updatePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        if (searchAndDeletePaymentDetails(paymentDTO)) {
            return savePaymentDetails(paymentDTO);
        }
        return false;
    }

    @Override
    public boolean searchAndDeletePaymentDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDetailsDAO.searchAndDeletePaymentDetails(new PaymentDetails(new PaymentDetailsPK(paymentDTO.getPId(), null), null, null));
    }

    @Override
    public boolean updateCourseDetails(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        if (searchAndDeleteCourses(paymentDTO.getSId())) {
            return saveCourseDetails(paymentDTO);
        }
        return false;
    }

    @Override
    public boolean searchAndDeleteCourses(String sId) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.searchAndDeleteCourses(sId);
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) {
        Transaction transaction = null;

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();  // Start transaction

            // Update payment
            if (updatePayment(paymentDTO)) {
                // Update payment details
                if (updatePaymentDetails(paymentDTO)) {
                    // Update course details
                    if (updateCourseDetails(paymentDTO)) {
                        transaction.commit();  // Commit the transaction
                        return true;
                    }
                }
            }

            if (transaction != null) {
                transaction.rollback();  // Rollback on failure
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback in case of an exception
            }
            e.printStackTrace();
            throw new RuntimeException("Transaction failed", e);
        }
    }


    @Override
    public PaymentDTO searchByPaymentId(String paymentId) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.searchById(new Payment(paymentId, null, null, null, null, null, null));
        return payment != null ? payment.toDTO() : null;
    }

    @Override
    public StudentDTO searchByStudentId(String studentId) throws SQLException, ClassNotFoundException {
        Student student = studentDAO.searchById(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
        return student != null ? student.toDTO() : null;
    }

    @Override
    public boolean isPaymentExist(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.isExist(new Payment(paymentId, null, null, null, null, null, null));
    }
}
