package lk.ijse.cas.dao.custom.impl;

import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.PaymentDAO;
import lk.ijse.cas.dao.custom.PaymentDetailsDAO;
import lk.ijse.cas.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    PaymentDetailsDAO paymentDetailsDAO = (PaymentDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT_DETAILS);


    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String prefix = lastId.substring(0, 1); // Assuming format is "PXXX"
            int numericPart = Integer.parseInt(lastId.substring(1)); // Extract numeric part
            int nextNumericPart = numericPart + 1;
            String nextId = prefix + String.format("%03d", nextNumericPart); // Format back to "PXXX" format
            return nextId;
        }

        return "P001";
    }

    @Override
    public boolean isAvailable(Payment entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment WHERE paymentId = ?", entity.getPId());

        return !rst.next();
    }

    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment VALUES (?, ?, ?, ?, ?, ?)",
                entity.getPId(),
                entity.getDesc(),
                entity.getDate(),
                entity.getMethod(),
                entity.getAmount(),
                entity.getSId()
                );
    }

    @Override
    public boolean isExist(Payment entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment WHERE paymentId = ?", entity.getPId());

        return rst.next();
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE payment SET description = ?, method = ?, amount = ?, studentId = ? WHERE paymentId = ?",
                entity.getDesc(),
                entity.getMethod(),
                entity.getAmount(),
                entity.getSId(),
                entity.getPId()
                );
    }

    @Override
    public boolean remove(Payment entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Payment searchById(Payment entity) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE paymentId = ?", entity.getPId());

        Payment payment = null;

        if (resultSet.next()) {
            String paymnetId = resultSet.getString(1);
            String desc = resultSet.getString(2);
            String date = resultSet.getString(3);
            String pMethod = resultSet.getString(4);
            String amount = resultSet.getString(5);
            String studentId = resultSet.getString(6);

            payment = new Payment(paymnetId, desc, date, pMethod, amount, studentId, paymentDetailsDAO.getPaymentDetails(paymnetId));
        }
        return payment;
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment");

        List<Payment> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            String paymnetId = resultSet.getString(1);
            String desc = resultSet.getString(2);
            String date = resultSet.getString(3);
            String pMethod = resultSet.getString(4);
            String amount = resultSet.getString(5);
            String studentId = resultSet.getString(6);

            Payment payment = new Payment(paymnetId, desc, date, pMethod, amount, studentId, paymentDetailsDAO.getPaymentDetails(paymnetId));

            paymentList.add(payment);
        }
        return paymentList;
    }
}
