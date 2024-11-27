package lk.ijse.cas.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.cas.bo.custom.RegistorBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public class RegistorBOImpl implements RegistorBO {
//    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public ObservableList<String> getAllEmployees() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.isAvailable(new User(userId, null, null, null));
    }

    @Override
    public boolean isEmployeeAvailable(String employeeId) throws SQLException, ClassNotFoundException {
        return userDAO.isEmployeeIdAvailable(employeeId);
    }

    @Override
    public boolean registor(UserDTO user) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(user.getUserId(), user.getUserName(), user.getPassword(), user.getEmployeeId()));
    }

    @Override
    public EmployeeDTO searchEmployeeById(String employeeId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.searchById(new Employee(employeeId, null, null, null, null, null, null, null, null, null, null));
        return new EmployeeDTO(employee.getId(), employee.getFname(), employee.getLname(), employee.getDOb(), employee.getGender(), employee.getJoinDate(), employee.getNIC(), employee.getAddress(), employee.getCNo(), employee.getEmail(), employee.getRole());
    }
}
