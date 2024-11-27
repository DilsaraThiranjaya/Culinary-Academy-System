package lk.ijse.cas.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.CrudDAO;
import lk.ijse.cas.entity.Student;

import java.sql.SQLException;

public interface StudentDAO extends CrudDAO<Student> {
    Student searchByCNo(String CNo) throws SQLException, ClassNotFoundException;

    String getStName(String id) throws SQLException, ClassNotFoundException;
}
