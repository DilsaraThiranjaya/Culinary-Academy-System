package lk.ijse.cas.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.cas.dao.SQLUtil;
import lk.ijse.cas.dao.custom.CourseDAO;
import lk.ijse.cas.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public boolean isAvailable(Course entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT courseId FROM course WHERE courseId = ?", entity.getId());

        return !rst.next();
    }

    @Override
    public boolean save(Course entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO course VALUES (?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDuration(),
                entity.getPrice()
                );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT courseId FROM course ORDER BY courseId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String prefix = lastId.substring(0, 1); // Assuming format is "CXXX"
            int numericPart = Integer.parseInt(lastId.substring(1)); // Extract numeric part
            int nextNumericPart = numericPart + 1;
            String nextId = prefix + String.format("%03d", nextNumericPart); // Format back to "CXXX" format
            return nextId;
        }

        return "C001";
    }

    @Override
    public boolean isExist(Course entity) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT courseId FROM course WHERE courseId = ?", entity.getId());

        return rst.next();
    }

    @Override
    public boolean update(Course entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE course SET name=?, description=?, duration=?, price=? WHERE courseId=?",
                entity.getName(),
                entity.getDescription(),
                entity.getDuration(),
                entity.getPrice(),
                entity.getId()
                );
    }

    @Override
    public boolean remove(Course entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM course WHERE courseId = ?", entity.getId());
    }

    @Override
    public Course searchById(Course entity) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM course WHERE courseId = ?", entity.getId());

        Course course = null;

        if (resultSet.next()) {
            String courseId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String duration = resultSet.getString(4);
            String price = resultSet.getString(5);

            course = new Course(courseId, name, description, duration, price);
        }
        return course;
    }

    @Override
    public List<Course> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM course");

        List<Course> list = new ArrayList<>();

        while (resultSet.next()) {
            String courseId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String duration = resultSet.getString(4);
            String price = resultSet.getString(5);

            Course course = new Course(courseId, name, description, duration, price);
            list.add(course);
        }
        return list;
    }

    @Override
    public ObservableList<String> getCourses() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM course");

        ObservableList<String> items = FXCollections.observableArrayList();

        while (resultSet.next()) {
            items.add(resultSet.getString("name"));
        }
        return items;
    }

    @Override
    public String getCourseName(String courseId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM course WHERE courseId = ?", courseId);

        String name = null;

        if (resultSet.next()) {
            name = resultSet.getString("name");

            return name;
        }
        return name;
    }

    @Override
    public String getCourseID(String courseName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT courseID FROM course WHERE name = ?", courseName);

        if(resultSet.next()){
            return resultSet.getString("courseId");
        }
        return null;
    }

    @Override
    public double getPrice(String selectedCourse) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT price FROM course WHERE name = ?", selectedCourse);

        double price = 0;

        if (resultSet.next()) {
            price = resultSet.getDouble("price");

            return price;
        }
        return price;
    }
}
