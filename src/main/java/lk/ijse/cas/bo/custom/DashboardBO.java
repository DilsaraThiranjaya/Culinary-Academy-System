package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.UserDTO;

import java.sql.SQLException;

public interface DashboardBO extends SuperBO {
    public UserDTO getRole(String userId) throws SQLException, ClassNotFoundException ;
}
