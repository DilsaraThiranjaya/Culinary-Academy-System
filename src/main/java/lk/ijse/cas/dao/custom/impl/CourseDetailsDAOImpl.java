package lk.ijse.cas.dao.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.CourseDetailsDAO;
import lk.ijse.cas.embedded.CourseDetailsPK;
import lk.ijse.cas.entity.CourseDetails;
import lk.ijse.cas.util.SessionFactoryConfig;
import lk.ijse.cas.view.tdm.CoursePriceTm;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsDAOImpl implements CourseDetailsDAO {

    private CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);

    @Override
    public boolean save(String sId, ObservableList<CoursePriceTm> cp) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            if (cp != null) {
                for (CoursePriceTm cpTm : cp) {
                    String studentId = sId;
                    if (studentId != null) {
                        // Create new CourseDetails object
                        CourseDetails courseDetails = new CourseDetails();
                        courseDetails.setCourseDetailsPK(new CourseDetailsPK(studentId, courseDAO.getCourseID(cpTm.getCourse())));
                        courseDetails.setStatus(null);  // Set default value for status
                        courseDetails.setStudent(null);
                        courseDetails.setCourse(null);

                        session.save(courseDetails);  // Save CourseDetails entity
                    }
                }
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean searchAndDeleteCourses(String sId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Fetch all courseDetails for the given studentId
            String hql = "FROM CourseDetails cd WHERE cd.courseDetailsPK.studentId = :studentId";
            Query<CourseDetails> query = session.createQuery(hql, CourseDetails.class);
            query.setParameter("studentId", sId);
            List<CourseDetails> courseDetailsList = query.list();

            boolean isDeleted = false;

            // Loop through the list and delete CourseDetails
            for (CourseDetails courseDetails : courseDetailsList) {
                session.delete(courseDetails);  // Delete CourseDetails entity
                isDeleted = true;
            }

            transaction.commit();
            return isDeleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(CourseDetails entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.delete(entity);  // Delete CourseDetails entity
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CourseDetails searchById(CourseDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isExist(CourseDetails entity) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Check if the CourseDetails exists for the given studentId and courseId
            String hql = "FROM CourseDetails cd WHERE cd.courseDetailsPK.studentId = :studentId AND cd.courseDetailsPK.programId = :programId";
            Query<CourseDetails> query = session.createQuery(hql, CourseDetails.class);
            query.setParameter("studentId", entity.getCourseDetailsPK().getStudentId());
            query.setParameter("programId", entity.getCourseDetailsPK().getProgramId());

            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CourseDetails> getAll() {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean save(CourseDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isAvailable(CourseDetails entity) {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String getNextId() {
        // Not implemented as per original
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(CourseDetails entity) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Update status in the courseDetails table
            String hql = "UPDATE CourseDetails cd SET cd.status = :status WHERE cd.courseDetailsPK.studentId = :studentId AND cd.courseDetailsPK.programId = :programId";
            Query query = session.createQuery(hql);
            query.setParameter("status", entity.getStatus());
            query.setParameter("studentId", entity.getCourseDetailsPK().getStudentId());
            query.setParameter("programId", entity.getCourseDetailsPK().getProgramId());

            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CourseDetails> getAllCourseDetails(String id) {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Fetch courseDetails for the given programId
            String hql = "FROM CourseDetails cd WHERE cd.courseDetailsPK.programId = :programId";
            Query<CourseDetails> query = session.createQuery(hql, CourseDetails.class);
            query.setParameter("programId", id);  // Bind the courseId parameter to the query

            return query.list();  // Execute the query and fetch the results
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();  // Return an empty list if an error occurs
        }
    }
}
