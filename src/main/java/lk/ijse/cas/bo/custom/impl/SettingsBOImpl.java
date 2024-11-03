package lk.ijse.cas.bo.custom.impl;

import lk.ijse.pos.bo.custom.SettingsBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.UserDAO;
import lk.ijse.pos.dto.UserDTO;
import lk.ijse.pos.entity.User;

import java.sql.SQLException;

public class SettingsBOImpl implements SettingsBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.isAvailable(new User(userId, null, null, null));
    }

    @Override
    public boolean updateUser(UserDTO userDTO, String userId) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmployeeId()), userId);
    }
}
