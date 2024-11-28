package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.ForgotPasswordBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;

public class ForgotPasswordBOImpl implements ForgotPasswordBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean isUserExist(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.isExist(new User(userId, null, null, null, null));
    }

    @Override
    public boolean changePassword(String userId, String pass) throws SQLException, ClassNotFoundException {
        return userDAO.changePassword(userId, pass);
    }


    @Override
    public UserDTO searchUserById(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.searchById(new User(userId, null, null, null, null));
        return user.toDTO();
    }
}
