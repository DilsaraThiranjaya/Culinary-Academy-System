package lk.ijse.cas.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.entity.Course;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public boolean isAvailable(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT 1 FROM Course c WHERE c.programId = :programId";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("programId", entity.getProgramId());
            return query.uniqueResult() == null;  // If no result, course is available
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);  // Save the entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getNextId() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT c.programId FROM Course c ORDER BY c.programId DESC";
            Query<String> query = session.createQuery(hql, String.class);
            List<String> ids = query.setMaxResults(1).list();

            if (!ids.isEmpty()) {
                String lastId = ids.get(0);
                String prefix = lastId.substring(0, 1); // Assuming format "CXXX"
                int numericPart = Integer.parseInt(lastId.substring(1));
                String nextId = prefix + String.format("%03d", numericPart + 1);
                return nextId;
            }
            return "C001";
        } catch (Exception e) {
            e.printStackTrace();
            return "C001";  // Default ID in case of error
        }
    }

    @Override
    public boolean isExist(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT 1 FROM Course c WHERE c.programId = :programId";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("programId", entity.getProgramId());
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);  // Update the entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);  // Delete the entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Course searchById(Course entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            return session.get(Course.class, entity.getProgramId());  // Fetch by ID
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> getAll() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "FROM Course";
            Query<Course> query = session.createQuery(hql, Course.class);
            return query.list();  // Return all courses
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ObservableList<String> getCourses() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT c.name FROM Course c";
            Query<String> query = session.createQuery(hql, String.class);
            return FXCollections.observableArrayList(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    @Override
    public String getCourseName(String courseId) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT c.name FROM Course c WHERE c.programId = :courseId";  // HQL query to fetch course name

            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("courseId", courseId);  // Set the courseId parameter in the query

            String name = query.uniqueResult();  // Fetch the unique result (course name)

            return name;  // Return the course name or null if not found
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null in case of an error
        }
    }

    @Override
    public String getCourseID(String courseName) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT c.programId FROM Course c WHERE c.name = :courseName";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("courseName", courseName);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public double getPrice(String selectedCourse) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT c.fee FROM Course c WHERE c.name = :courseName";
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("courseName", selectedCourse);
            Double price = query.uniqueResult();
            return price != null ? price : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
