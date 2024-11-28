package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.SettingsBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public class SettingsBOImpl implements SettingsBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean isUserAvailable(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.isAvailable(new User(userId, null, null, null, null));
    }

    @Override
    public boolean updateUser(UserDTO userDTO, String userId) throws SQLException, ClassNotFoundException {
        User updatedUser = new User(
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPosition(),
                userDTO.getPassword(),
                userDTO.getEmail()
        );
        return userDAO.update(updatedUser, userId);
    }

    @Override
    public UserDTO searchUserById(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.searchById(new User(userId, null, null, null, null));
        return user.toDTO();
    }

    @Override
    public boolean registor(UserDTO user) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(user.getUserId(), user.getUserName(), user.getPosition(), user.getPassword(), user.getEmail()));
    }

    @Override
    public UserDTO getRole(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.searchById(new User(userId, null, null, null, null));
        return user.toDTO();
    }
}
