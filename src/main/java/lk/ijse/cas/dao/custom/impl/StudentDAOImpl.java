package lk.ijse.cas.dao.custom.impl;

import lk.ijse.cas.dao.custom.StudentDAO;
import lk.ijse.cas.entity.Student;
import lk.ijse.cas.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Use HQL to fetch the last studentId in descending order
            String hql = "SELECT s.studentId FROM Student s ORDER BY s.studentId DESC";
            Query<String> query = session.createQuery(hql, String.class);
            query.setMaxResults(1); // Limit to one result
            String lastId = query.uniqueResult();

            if (lastId != null) {
                // Extract prefix and numeric part
                String prefix = lastId.substring(0, 2); // Assuming format is "STXXX"
                int numericPart = Integer.parseInt(lastId.substring(2)); // Extract numeric part
                int nextNumericPart = numericPart + 1;
                // Format the new ID
                return prefix + String.format("%03d", nextNumericPart); // Format back to "STXXX" format
            }

            // If no IDs exist, return the first ID
            return "ST001";
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }

    @Override
    public boolean isAvailable(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Student student = session.get(Student.class, entity.getStudentId());
            return student == null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(entity);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean isExist(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Student student = session.get(Student.class, entity.getStudentId());
            return !(student == null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(entity);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public Student searchById(Student entity) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            return session.get(Student.class, entity.getStudentId()); // Fetches the Student entity by ID
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Student searchByCNo(String CNo) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "FROM Student s WHERE s.contactNo = :contactNo";  // HQL query to search by contactNo
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("contactNo", CNo);  // Set the contact number parameter

            Student student = query.uniqueResult();  // Fetch a unique result (or null if not found)

            // If student is found, we can manually map or directly return it
            return student;  // Return the student or null if not found
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null if an error occurs
        }
    }

    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        List<Student> studentList = new ArrayList<>();

        try(Session session = SessionFactoryConfig.getInstance().getSession()){
            studentList = (ArrayList<Student>) session.createQuery("FROM Student").list();
        }catch (Exception e){
            e.printStackTrace();
        }

        return studentList;
    }

    @Override
    public String getStName(String id) throws SQLException, ClassNotFoundException {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT CONCAT(s.firstName, ' ', s.lastName) FROM Student s WHERE s.studentId = :studentId";  // HQL query

            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("studentId", id);  // Bind the student ID parameter to the query

            String fullName = query.uniqueResult();  // Fetch the concatenated full name

            return fullName;  // Return the full name or null if not found
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null if an error occurs
        }
    }
}
