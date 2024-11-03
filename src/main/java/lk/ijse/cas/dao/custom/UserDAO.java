package lk.ijse.cas.dao.custom;

import lk.ijse.cas.dao.CrudDAO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean update(User entity, String userId) throws SQLException, ClassNotFoundException;

    boolean changePassword(String userId, String pass) throws SQLException, ClassNotFoundException;

    String getEmployeeId(String userId) throws SQLException, ClassNotFoundException;

    boolean isEmployeeIdAvailable(String employeeId) throws SQLException, ClassNotFoundException;
}
