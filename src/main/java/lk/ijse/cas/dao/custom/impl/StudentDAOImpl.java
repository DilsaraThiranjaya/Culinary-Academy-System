package lk.ijse.cas.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.cas.dao.SQLUtil;
import lk.ijse.cas.dao.custom.StudentDAO;
import lk.ijse.cas.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT studentId FROM student ORDER BY studentId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String prefix = lastId.substring(0, 2); // Assuming format is "STXXX"
            int numericPart = Integer.parseInt(lastId.substring(2)); // Extract numeric part
            int nextNumericPart = numericPart + 1;
            String nextId = prefix + String.format("%03d", nextNumericPart); // Format back to "STXXX" format
            return nextId;
        }

        return "ST001";
    }

    @Override
    public boolean isAvailable(Student entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT studentId FROM student WHERE studentId = ?", entity.getId());

        return !rst.next();
    }

    @Override
    public boolean save(Student entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getFname(),
                entity.getLname(),
                entity.getDOb(),
                entity.getGender(),
                entity.getAdmissionDate(),
                entity.getNIC(),
                entity.getAddress(),
                entity.getCNo(),
                entity.getEmail()
                );
    }

    @Override
    public boolean update(Student entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE student SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, admissionDate = ?, NIC = ?, address = ?, contactNo = ?, email = ? WHERE studentId = ?",
                entity.getFname(),
                entity.getLname(),
                entity.getDOb(),
                entity.getGender(),
                entity.getAdmissionDate(),
                entity.getNIC(),
                entity.getAddress(),
                entity.getCNo(),
                entity.getEmail(),
                entity.getId()
                );
    }

    @Override
    public boolean isExist(Student entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT studentId FROM student WHERE studentId = ?", entity.getId());

        return rst.next();
    }

    @Override
    public boolean remove(Student entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM student WHERE studentId = ?", entity.getId());
    }

    @Override
    public Student searchById(Student entity) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM student WHERE studentId = ?", entity.getId());

        Student student = null;

        if (resultSet.next()) {
            String studentId = resultSet.getString(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String dateOfBirth = resultSet.getString(4);
            String gender = resultSet.getString(5);
            String admissionDate = resultSet.getString(6);
            String nic = resultSet.getString(7);
            String address = resultSet.getString(8);
            String contactNo = resultSet.getString(9);
            String email = resultSet.getString(10);

            student = new Student(studentId, fname, lname, dateOfBirth, gender, admissionDate, nic, address, contactNo, email);
        }
        return student;
    }

    @Override
    public Student searchByCNo(String CNo) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM student WHERE contactNo = ?", CNo);

        Student student = null;

        if (resultSet.next()) {
            String studentId = resultSet.getString(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String dateOfBirth = resultSet.getString(4);
            String gender = resultSet.getString(5);
            String admissionDate = resultSet.getString(6);
            String nic = resultSet.getString(7);
            String address = resultSet.getString(8);
            String contactNo = resultSet.getString(9);
            String email = resultSet.getString(10);

            student = new Student(studentId, fname, lname, dateOfBirth, gender, admissionDate, nic, address, contactNo, email);
        }
        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM student");

        List<Student> studentList = new ArrayList<>();

        while (resultSet.next()) {
            String studentId = resultSet.getString(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String dateOfBirth = resultSet.getString(4);
            String gender = resultSet.getString(5);
            String admissionDate = resultSet.getString(6);
            String nic = resultSet.getString(7);
            String address = resultSet.getString(8);
            String contactNo = resultSet.getString(9);
            String email = resultSet.getString(10);

            Student student = new Student(studentId, fname, lname, dateOfBirth, gender, admissionDate, nic, address, contactNo, email);

            studentList.add(student);
        }
        return studentList;
    }

    @Override
    public String getStName(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT firstName, lastName FROM student WHERE studentId = ?", id);

        if(resultSet.next()) {
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);

            return firstName + " " + lastName;
        }
        return null;
    }

    @Override
    public ObservableList<String> getAllStudents() throws SQLException, ClassNotFoundException {
        ObservableList<String> items = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("select studentId from student");

        while (resultSet.next()) {
            items.add(resultSet.getString("studentId"));
        }
        return items;
    }
}
