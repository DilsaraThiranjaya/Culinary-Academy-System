package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.LoginBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public UserDTO searchUserById(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.searchById(new User(userId, null, null, null, null));
        return user != null ? user.toDTO() : null;
    }
}
