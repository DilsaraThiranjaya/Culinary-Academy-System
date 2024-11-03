package lk.ijse.cas.bo.custom;

import lk.ijse.cas.bo.SuperBO;
import lk.ijse.cas.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    public List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException ;

    public String getNextStudentId() throws SQLException, ClassNotFoundException ;

    public boolean isStudentExist(String studentId) throws SQLException, ClassNotFoundException ;

    public boolean removeStudent(String studentId) throws SQLException, ClassNotFoundException ;

    public boolean isStudentAvailable(String studentId) throws SQLException, ClassNotFoundException ;

    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException ;

    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException ;

    public StudentDTO searchByStudentId(String studentId) throws SQLException, ClassNotFoundException ;

    public StudentDTO searchByCNo(String cNo) throws SQLException, ClassNotFoundException ;
}
