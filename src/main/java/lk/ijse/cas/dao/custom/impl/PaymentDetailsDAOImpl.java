package lk.ijse.cas.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.PaymentDetailsDAO;
import lk.ijse.cas.entity.PaymentDetails;
import lk.ijse.cas.view.tdm.CoursePriceTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentDetailsDAOImpl implements PaymentDetailsDAO {
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);

    @Override
    public boolean save(PaymentDetails entity, ObservableList<CoursePriceTm> cp) throws SQLException, ClassNotFoundException {
        boolean isSaved = false;

        if(cp != null){
            for(CoursePriceTm cpTm : cp){
                String paymentId = entity.getPId();
                if(paymentId != null){
                    isSaved =  SQLUtil.execute("INSERT INTO paymentDetails VALUES (?, ?)", paymentId, courseDAO.getCourseID(cpTm.getCourse()));
                }
            }
            return isSaved;
        }

        return false;
    }

    @Override
    public boolean searchAndDeletePaymentDetails(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT courseId FROM paymentDetails WHERE paymentId = ?", entity.getPId());

        boolean isDeleted = false;

        while(resultSet.next()){
            PaymentDetails paymentDetails = new PaymentDetails(entity.getPId(), resultSet.getString("courseId"));

            isDeleted = remove(paymentDetails);
        }
        return isDeleted;
    }

    @Override
    public boolean save(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isAvailable(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean remove(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM paymentDetails WHERE courseId = ? AND paymentId = ?", entity.getCId(), entity.getPId());
    }

    @Override
    public PaymentDetails searchById(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isExist(PaymentDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public List<PaymentDetails> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public ObservableList<CoursePriceTm> getPaymentDetails(String pId) throws SQLException, ClassNotFoundException {
        ObservableList<CoursePriceTm> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT courseId FROM paymentDetails WHERE paymentId = ?", pId);

        while(resultSet.next()){
            CoursePriceTm coursePriceTm = new CoursePriceTm(
                    courseDAO.getCourseName(resultSet.getString("courseId")),
                    courseDAO.getPrice(courseDAO.getCourseName(resultSet.getString("courseId")))
            );
            list.add(coursePriceTm);
        }
        return list;
    }
}
