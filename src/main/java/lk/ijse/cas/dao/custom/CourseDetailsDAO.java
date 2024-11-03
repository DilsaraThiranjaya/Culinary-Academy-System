package lk.ijse.cas.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.CrudDAO;
import lk.ijse.cas.entity.CourseDetails;
import lk.ijse.cas.view.tdm.CoursePriceTm;

import java.sql.SQLException;
import java.util.List;

public interface CourseDetailsDAO extends CrudDAO<CourseDetails> {
    boolean save(String sId, ObservableList<CoursePriceTm> cp) throws SQLException, ClassNotFoundException;

    boolean searchAndDeleteCourses(String sId) throws SQLException, ClassNotFoundException;

    List<CourseDetails> getAllCourseDetails(String id) throws SQLException, ClassNotFoundException;
}
