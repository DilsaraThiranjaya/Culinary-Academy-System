package lk.ijse.cas.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.CrudDAO;
import lk.ijse.cas.entity.PaymentDetails;
import lk.ijse.cas.view.tdm.CoursePriceTm;

import java.sql.SQLException;

public interface PaymentDetailsDAO extends CrudDAO<PaymentDetails> {
    boolean save(PaymentDetails entity, ObservableList<CoursePriceTm> cp) throws SQLException, ClassNotFoundException;

    boolean searchAndDeletePaymentDetails(PaymentDetails entity) throws SQLException, ClassNotFoundException;

    ObservableList<CoursePriceTm> getPaymentDetails(String pId) throws SQLException, ClassNotFoundException;
}
