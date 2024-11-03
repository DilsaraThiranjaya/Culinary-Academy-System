package lk.ijse.cas.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.UserDTO;

import java.sql.SQLException;

public interface RegistorBO extends SuperBO {
    public ObservableList<String> getAllEmployees() throws SQLException, ClassNotFoundException ;

    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException ;

    public boolean isEmployeeAvailable(String employeeId) throws SQLException, ClassNotFoundException ;

    public boolean registor(UserDTO user) throws SQLException, ClassNotFoundException ;

    public EmployeeDTO searchEmployeeById(String employeeId) throws SQLException, ClassNotFoundException ;
}
