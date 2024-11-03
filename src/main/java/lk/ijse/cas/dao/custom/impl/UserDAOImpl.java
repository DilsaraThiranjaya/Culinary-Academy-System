package lk.ijse.cas.dao.custom.impl;

import lk.ijse.cas.dao.SQLUtil;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User searchById (User entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT userId, userName, password, employeeId FROM user WHERE userId = ?", entity.getUserId());
        if(rst.next()){
            User user = new User(
                rst.getString("userId"),
                rst.getString("userName"),
                rst.getString("password"),
                rst.getString("employeeId")
            );
            return user;
        }
        return null;
    }

    @Override
    public boolean isAvailable(User entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT userId FROM user WHERE userId = ?", entity.getUserId());

        return !rst.next();
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean remove(User entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(User entity, String userId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET userName = ?, password = ?, userID = ? WHERE userId = ?",
                entity.getUserName(),
                entity.getPassword(),
                entity.getUserId(),
                userId
                );
    }

    @Override
    public boolean isExist(User entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE userId = ?", entity.getUserId());

        return rst.next();
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean changePassword(String userId, String pass) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET password =? WHERE userId=?", pass, userId);
    }

    @Override
    public String getEmployeeId(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT employeeId FROM user WHERE userId = ?", userId);

        if (resultSet.next()) {
            String employeeId = resultSet.getString("employeeId");
            return employeeId;
        } else {
            // Handle the case where the user ID is not found
            return null;
        }
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?, ?)",
                entity.getUserId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmployeeId()
                );
    }

    @Override
    public boolean isEmployeeIdAvailable(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT userId FROM user WHERE employeeId = ?", employeeId);

        return !rst.next();
    }
}
