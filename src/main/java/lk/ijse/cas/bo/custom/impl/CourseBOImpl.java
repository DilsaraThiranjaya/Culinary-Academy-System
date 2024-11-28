package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.CourseBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.CourseDetailsDAO;
import lk.ijse.cas.dao.custom.StudentDAO;
import lk.ijse.cas.dao.custom.UserDAO;
import lk.ijse.cas.dto.CourseDTO;
import lk.ijse.cas.dto.CourseDetailsDTO;
import lk.ijse.cas.dto.UserDTO;
import lk.ijse.cas.entity.Course;
import lk.ijse.cas.entity.CourseDetails;
import lk.ijse.cas.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);
    CourseDetailsDAO courseDetailsDAO = (CourseDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE_DETAILS);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public List<CourseDetailsDTO> getAllCourseDetails(String id) throws SQLException, ClassNotFoundException {
        List<CourseDetails> courseDetailsList = courseDetailsDAO.getAllCourseDetails(id);
        List<CourseDetailsDTO> courseDetailsDTOs = new ArrayList<>();

        for (CourseDetails courseDetails : courseDetailsList) {
            courseDetailsDTOs.add(courseDetails.toDTO());
        }
        return courseDetailsDTOs;
    }

    @Override
    public List<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        List<Course> courseList = courseDAO.getAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courseList) {
            courseDTOs.add(course.toDTO());
        }
        return courseDTOs;
    }

    @Override
    public String getNextCourseId() throws SQLException, ClassNotFoundException {
        return courseDAO.getNextId();
    }

    @Override
    public boolean isCourseExist(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.isExist(new Course(courseId, null, null, null, null, null));
    }

    @Override
    public boolean removeCourse(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.remove(new Course(courseId, null, null, null, null, null));
    }

    @Override
    public boolean isCourseAvailable(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.isAvailable(new Course(courseId, null, null, null, null, null));
    }

    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return courseDAO.save(courseDTO.toEntity());
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return courseDAO.update(courseDTO.toEntity());
    }

    @Override
    public CourseDTO searchByCourseId(String courseId) throws SQLException, ClassNotFoundException {
        Course course = courseDAO.searchById(new Course(courseId, null, null, null, null, null));
        return course != null ? course.toDTO() : null;
    }

    @Override
    public String getCourseName(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.getCourseName(courseId);
    }

    @Override
    public UserDTO getRole(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.searchById(new User(userId, null, null, null, null));
        return user.toDTO();
    }

    @Override
    public boolean isCourseDetailExist(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.isExist(courseDetailsDTO.toEntity());
    }

    @Override
    public boolean updateCourseStatus(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.update(courseDetailsDTO.toEntity());
    }

    @Override
    public String getStName(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.getStName(id);
    }
}
