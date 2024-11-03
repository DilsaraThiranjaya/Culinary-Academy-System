package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.UserDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    public UserDTO searchUserById (String userId) throws SQLException, ClassNotFoundException ;
}
