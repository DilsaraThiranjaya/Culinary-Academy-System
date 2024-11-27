package lk.ijse.cas.bo.custom.impl;

import lk.ijse.cas.bo.custom.StudentBO;
import lk.ijse.cas.dao.DAOFactory;
import lk.ijse.cas.dao.custom.StudentDAO;
import lk.ijse.cas.dto.StudentDTO;
import lk.ijse.cas.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);


    @Override
    public List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException {
        List<Student> studentList = studentDAO.getAll();
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : studentList) {
            studentDTOs.add(student.toDTO());
        }
        return studentDTOs;
    }

    @Override
    public String getNextStudentId() throws SQLException, ClassNotFoundException {
        return studentDAO.getNextId() ;
    }

    @Override
    public boolean isStudentExist(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.isExist(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public boolean removeStudent(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.remove(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public boolean isStudentAvailable(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.isAvailable(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDAO.save(studentDTO.toEntity());
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDAO.update(studentDTO.toEntity());
    }

    @Override
    public StudentDTO searchByStudentId(String studentId) throws SQLException, ClassNotFoundException {
        Student student = studentDAO.searchById(new Student(studentId, null, null, null, null, null, null, null, null, null, null, null));
        return student != null ? student.toDTO() : null;
    }

    @Override
    public StudentDTO searchByCNo(String cNo) throws SQLException, ClassNotFoundException {
        Student student = studentDAO.searchByCNo(cNo);
        return student != null ? student.toDTO() : null;
    }
}
