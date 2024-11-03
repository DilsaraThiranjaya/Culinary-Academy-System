package lk.ijse.cas.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.cas.dao.CrudDAO;
import lk.ijse.cas.entity.Course;

import java.sql.SQLException;

public interface CourseDAO extends CrudDAO<Course> {
    String getCourseID(String courseName) throws SQLException, ClassNotFoundException;

    ObservableList<String> getCourses() throws SQLException, ClassNotFoundException;

    String getCourseName(String courseId) throws SQLException, ClassNotFoundException;

    double getPrice(String selectedCourse) throws SQLException, ClassNotFoundException;
}
