package lk.ijse.cas.dao.custom.impl;

import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.entity.User;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User searchById(User entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Fetch the user by ID
            return session.get(User.class, entity.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isAvailable(User entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Check if a user exists by userId
            User foundUser = session.get(User.class, entity.getUserId());
            return foundUser == null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getNextId() {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean remove(User entity) {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(User entity, String userId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            // Fetch the existing user
            User existingUser = session.get(User.class, userId);
            if (existingUser != null) {
                // Update the fields
                existingUser.setUserName(entity.getUserName());
                existingUser.setPassword(entity.getPassword());
                existingUser.setUserId(entity.getUserId());
                session.update(existingUser); // Update the entity in the database
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isExist(User entity) {
        return !isAvailable(entity); // Reuse `isAvailable` for checking existence
    }

    @Override
    public List<User> getAll() {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean changePassword(String userId, String pass) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            // Fetch the user by ID
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setPassword(pass); // Update the password
                session.update(user); // Persist the changes
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(User entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();
            // Save the user entity
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
