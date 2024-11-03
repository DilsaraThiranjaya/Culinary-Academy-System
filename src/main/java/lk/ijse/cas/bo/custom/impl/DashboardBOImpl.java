package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.DashboardBO;
import lk.ijse.cas.dao.DAOFactory;

import java.sql.SQLException;

public class DashboardBOImpl implements DashboardBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);


    @Override
    public String getRole(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.getRole(employeeId);
    }
}
