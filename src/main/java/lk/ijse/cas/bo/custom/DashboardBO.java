package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;

import java.sql.SQLException;

public interface DashboardBO extends SuperBO {
    public String getRole(String employeeId) throws SQLException, ClassNotFoundException ;
}
