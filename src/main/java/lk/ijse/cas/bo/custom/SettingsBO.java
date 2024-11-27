package lk.ijse.cas.bo.custom;

import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.bo.SuperBO;

import java.sql.SQLException;

public interface SettingsBO extends SuperBO {
    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException ;

    public boolean updateUser(UserDTO userDTO, String userId) throws SQLException, ClassNotFoundException ;

    public UserDTO searchUserById(String userId) throws SQLException, ClassNotFoundException;

    public boolean registor(UserDTO user) throws SQLException, ClassNotFoundException ;
}
