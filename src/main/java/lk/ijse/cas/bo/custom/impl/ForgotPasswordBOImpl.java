package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.ForgotPasswordBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public class ForgotPasswordBOImpl implements ForgotPasswordBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);


    @Override
    public boolean isUserExist(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.isExist(new User(userId, null, null, null));
    }

    @Override
    public boolean changePassword(String userId, String pass) throws SQLException, ClassNotFoundException {
        return userDAO.changePassword(userId, pass);
    }

    @Override
    public String getEmployeeId(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.getEmployeeId(userId);
    }

    @Override
    public EmployeeDTO searchEmployeeById(String employeeId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.searchById(new Employee(employeeId, null, null, null, null, null, null, null, null, null, null));
        return new EmployeeDTO(employee.getId(), employee.getFname(), employee.getLname(), employee.getDOb(), employee.getGender(), employee.getJoinDate(), employee.getNIC(), employee.getAddress(), employee.getCNo(), employee.getEmail(), employee.getRole());
    }
}
