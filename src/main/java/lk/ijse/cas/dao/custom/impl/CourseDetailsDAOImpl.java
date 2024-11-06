package lk.ijse.cas.dao.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.CourseDetailsDAO;
import lk.ijse.cas.entity.CourseDetails;
import lk.ijse.cas.view.tdm.CoursePriceTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailsDAOImpl implements CourseDetailsDAO {

    @Override
    public boolean save(String sId, ObservableList<CoursePriceTm> cp) throws SQLException, ClassNotFoundException {
        boolean isSaved = false;

        if(cp != null){
            for(CoursePriceTm cpTm : cp){
                String studentId = sId;
                if(studentId != null){
                    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);
                    isSaved = SQLUtil.execute("INSERT INTO courseDetails VALUES (?, ?, NULL)", sId, courseDAO.getCourseID(cpTm.getCourse()));
                }
            }
            return isSaved;
        }

        return false;
    }

    @Override
    public boolean searchAndDeleteCourses(String sId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT courseId FROM courseDetails WHERE studentId = ?", sId);

        boolean isDeleted = false;

        while(resultSet.next()){
            CourseDetails courseDetails  = new CourseDetails(sId, resultSet.getString("courseId"), null);
            remove(courseDetails);

            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean remove(CourseDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM courseDetails WHERE courseId = ? AND studentId = ?", entity.getCId(), entity.getSId());
    }

    @Override
    public CourseDetails searchById(CourseDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isExist(CourseDetails entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM courseDetails WHERE courseId = ? AND studentId = ?", entity.getCId(), entity.getSId());

        return rst.next();
    }

    @Override
    public List<CourseDetails> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean save(CourseDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean isAvailable(CourseDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(CourseDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE courseDetails SET status=? WHERE courseId=? AND studentId=?", entity.getStatus(), entity.getCId(), entity.getSId());
    }

    @Override
    public List<CourseDetails> getAllCourseDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM courseDetails WHERE courseId = ?", id);

        List<CourseDetails> list = new ArrayList<>();

        while (resultSet.next()) {
            String studentId = resultSet.getString(1);
            String status = resultSet.getString(3);

            CourseDetails cd = new CourseDetails(id, studentId, status);
            list.add(cd);
        }
        return list;
    }
}
