package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;

import java.sql.SQLException;

public interface ForgotPasswordBO extends SuperBO {
    public boolean isUserExist(String userId) throws SQLException, ClassNotFoundException ;

    public boolean changePassword(String userId, String pass) throws SQLException, ClassNotFoundException ;

    public String getEmployeeId(String userId) throws SQLException, ClassNotFoundException ;

    public EmployeeDTO searchEmployeeById(String employeeId) throws SQLException, ClassNotFoundException ;
}
