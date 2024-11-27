package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.CourseDTO;
import lk.ijse.cas.dto.CourseDetailsDTO;

import java.sql.SQLException;
import java.util.List;

public interface CourseBO extends SuperBO {
    public List<CourseDetailsDTO> getAllCourseDetails(String id) throws SQLException, ClassNotFoundException ;

    public List<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException ;

    public String getNextCourseId() throws SQLException, ClassNotFoundException ;

    public boolean isCourseExist(String courseId) throws SQLException, ClassNotFoundException ;

    public boolean removeCourse(String courseId) throws SQLException, ClassNotFoundException;

    public boolean isCourseAvailable(String courseId) throws SQLException, ClassNotFoundException ;

    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException ;

    public boolean isCourseDetailExist(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException ;

    public boolean updateCourseStatus(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException ;

    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException ;

    public String getStName(String id) throws SQLException, ClassNotFoundException ;

    public CourseDTO searchByCourseId(String courseId) throws SQLException, ClassNotFoundException ;

    public String getCourseName(String courseId) throws SQLException, ClassNotFoundException;
}
