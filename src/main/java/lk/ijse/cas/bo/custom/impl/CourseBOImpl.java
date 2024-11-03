package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.CourseBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.dao.custom.CourseDetailsDAO;
import lk.ijse.cas.dao.custom.StudentDAO;
import lk.ijse.cas.dto.CourseDTO;
import lk.ijse.cas.dto.CourseDetailsDTO;
import lk.ijse.cas.entity.Course;
import lk.ijse.cas.entity.CourseDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);
    CourseDetailsDAO courseDetailsDAO = (CourseDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE_DETAILS);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);


    @Override
    public List<CourseDetailsDTO> getAllCourseDetails(String id) throws SQLException, ClassNotFoundException {
        List<CourseDetails> courseDetailsList = courseDetailsDAO.getAllCourseDetails(id);
        List<CourseDetailsDTO> courseDetailsDTOs = new ArrayList<>();

        for (CourseDetails courseDetails : courseDetailsList) {
            CourseDetailsDTO courseDetailsDTO = new CourseDetailsDTO(
                    courseDetails.getCId(),
                    courseDetails.getSId(),
                    courseDetails.getStatus()
            );

            courseDetailsDTOs.add(courseDetailsDTO);
        }
        return courseDetailsDTOs;
    }

    @Override
    public List<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        List<Course> courseList = courseDAO.getAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courseList) {
            CourseDTO courseDTO = new CourseDTO(
                    course.getId(),
                    course.getName(),
                    course.getDescription(),
                    course.getDuration(),
                    course.getPrice()
            );

            courseDTOs.add(courseDTO);
        }
        return courseDTOs;
    }

    @Override
    public String getNextCourseId() throws SQLException, ClassNotFoundException {
        return courseDAO.getNextId();
    }

    @Override
    public boolean isCourseExist(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.isExist(new Course(courseId, null, null, null, null));
    }

    @Override
    public boolean removeCourse(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.remove(new Course(courseId, null, null, null, null));
    }

    @Override
    public boolean isCourseAvailable(String courseId) throws SQLException, ClassNotFoundException {
        return courseDAO.isAvailable(new Course(courseId, null, null, null, null));
    }

    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return courseDAO.save(new Course(
                courseDTO.getId(),
                courseDTO.getName(),
                courseDTO.getDescription(),
                courseDTO.getDuration(),
                courseDTO.getPrice()
        ));
    }

    @Override
    public boolean isCourseDetailExist(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.isExist(new CourseDetails(
                courseDetailsDTO.getStudentId(),
                courseDetailsDTO.getCourseId(),
                courseDetailsDTO.getStatus()
        ));
    }

    @Override
    public boolean updateCourseStatus(CourseDetailsDTO courseDetailsDTO) throws SQLException, ClassNotFoundException {
        return courseDetailsDAO.update(new CourseDetails(
                courseDetailsDTO.getStudentId(),
                courseDetailsDTO.getCourseId(),
                courseDetailsDTO.getStatus()
        ));
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return courseDAO.update(new Course(
                courseDTO.getId(),
                courseDTO.getName(),
                courseDTO.getDescription(),
                courseDTO.getDuration(),
                courseDTO.getPrice()
        ));
    }

    @Override
    public String getStName(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.getStName(id);
    }

    @Override
    public CourseDTO searchByCourseId(String courseId) throws SQLException, ClassNotFoundException {
        Course course = courseDAO.searchById(new Course(courseId, null, null, null, null));
        return course != null ? new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getDuration(),
                course.getPrice()
        ) : null;
    }
}
