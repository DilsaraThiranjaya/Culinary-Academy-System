package lk.ijse.cas.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.PaymentDetailsDAO;
import lk.ijse.cas.embedded.PaymentDetailsPK;
import lk.ijse.cas.entity.PaymentDetails;
import lk.ijse.cas.view.tdm.CoursePriceTm;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDetailsDAOImpl implements PaymentDetailsDAO {
    private CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);

    @Override
    public boolean save(PaymentDetails entity, ObservableList<CoursePriceTm> cp) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            if (cp != null) {
                for (CoursePriceTm cpTm : cp) {
                    String paymentId = entity.getPaymentDetailsPK().getPaymentId();
                    if (paymentId != null) {
                        // Create new PaymentDetails object
                        PaymentDetails paymentDetails = new PaymentDetails(new PaymentDetailsPK(entity.getPaymentDetailsPK().getPaymentId(), courseDAO.getCourseID(cpTm.getCourse())), null, null);
                        session.save(paymentDetails);  // Save PaymentDetails entity
                    }
                }
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean searchAndDeletePaymentDetails(PaymentDetails entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Fetch all courseIds for a given paymentId
            String hql = "FROM PaymentDetails pd WHERE pd.paymentDetailsPK.paymentId = :paymentId";
            List<PaymentDetails> paymentDetailsList = session.createQuery(hql, PaymentDetails.class)
                    .setParameter("paymentId", entity.getPaymentDetailsPK().getPaymentId())
                    .getResultList();

            boolean isDeleted = false;

            // Loop through the list and delete PaymentDetails
            for (PaymentDetails pd : paymentDetailsList) {
                session.delete(pd);  // Delete PaymentDetails entity
                isDeleted = true;
            }

            transaction.commit();
            return isDeleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(PaymentDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isAvailable(PaymentDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String getNextId() {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(PaymentDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean remove(PaymentDetails entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.delete(entity);  // Delete PaymentDetails entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PaymentDetails searchById(PaymentDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isExist(PaymentDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public List<PaymentDetails> getAll() {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public ObservableList<CoursePriceTm> getPaymentDetails(String pId) {
        ObservableList<CoursePriceTm> list = FXCollections.observableArrayList();

        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Fetch courseIds for the given paymentId
            String hql = "SELECT pd.paymentDetailsPK.programId FROM PaymentDetails pd WHERE pd.paymentDetailsPK.paymentId = :paymentId";
            List<String> courseIds = session.createQuery(hql, String.class)
                    .setParameter("paymentId", pId)
                    .getResultList();

            // Populate the list with CoursePriceTm objects
            for (String courseId : courseIds) {
                String courseName = courseDAO.getCourseName(courseId);
                double price = courseDAO.getPrice(courseName);
                CoursePriceTm coursePriceTm = new CoursePriceTm(courseName, price);
                list.add(coursePriceTm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
