package lk.ijse.cas.dao.custom.impl;

import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.PaymentDAO;
import lk.ijse.cas.dao.custom.PaymentDetailsDAO;
import lk.ijse.cas.entity.Payment;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    private PaymentDetailsDAO paymentDetailsDAO = (PaymentDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT_DETAILS);

    @Override
    public String getNextId() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Fetch the last paymentId from the "payments" table
            String hql = "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC";
            List<String> result = session.createQuery(hql, String.class).setMaxResults(1).getResultList();

            if (!result.isEmpty()) {
                String lastId = result.get(0);
                String prefix = lastId.substring(0, 1); // Assuming format is "PXXX"
                int numericPart = Integer.parseInt(lastId.substring(1)); // Extract numeric part
                int nextNumericPart = numericPart + 1;
                return prefix + String.format("%03d", nextNumericPart); // Format back to "PXXX" format
            }
            return "P001";
        }
    }

    @Override
    public boolean isAvailable(Payment entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Check if a payment with the provided paymentId already exists
            Payment payment = session.get(Payment.class, entity.getPaymentId());
            return payment == null;
        }
    }

    @Override
    public boolean save(Payment entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.save(entity); // Save the payment entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isExist(Payment entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Check if the payment exists in the database by paymentId
            Payment payment = session.get(Payment.class, entity.getPaymentId());
            return payment != null;
        }
    }

    @Override
    public boolean update(Payment entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.update(entity); // Update the payment entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Payment entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.delete(entity); // Delete the payment entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Payment searchById(Payment entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Payment payment = session.get(Payment.class, entity.getPaymentId());
            if (payment != null) {
                // Fetch PaymentDetails associated with this payment
                return payment;
            }
            return payment;
        }
    }

    @Override
    public List<Payment> getAll() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            List<Payment> paymentList = session.createQuery("FROM Payment", Payment.class).getResultList();
            return paymentList;
        }
    }
}
