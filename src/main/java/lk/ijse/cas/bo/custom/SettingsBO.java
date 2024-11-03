package lk.ijse.cas.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.UserDTO;

import java.sql.SQLException;

public interface SettingsBO extends SuperBO {
    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException ;

    public boolean updateUser(UserDTO userDTO, String userId) throws SQLException, ClassNotFoundException ;
}
