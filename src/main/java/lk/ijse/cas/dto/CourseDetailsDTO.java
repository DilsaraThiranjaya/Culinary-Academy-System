package lk.ijse.cas.dto;

import java.io.Serializable;

import lk.ijse.cas.entity.Course;
import lk.ijse.cas.entity.CourseDetails;
import lk.ijse.cas.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDetailsDTO implements Serializable {
    private String courseId;
    private String studentId;
    private String status;

    public CourseDetails toEntity() {
        CourseDetails courseDetailsEntity = new CourseDetails();
        courseDetailsEntity.setStatus(this.status);

        Student student = new Student();
        student.setStudentId(this.studentId);
        courseDetailsEntity.setStudent(student);

        Course course = new Course();
        course.setProgramId(this.courseId);
        courseDetailsEntity.setCourse(course);

        return courseDetailsEntity;
    }
}