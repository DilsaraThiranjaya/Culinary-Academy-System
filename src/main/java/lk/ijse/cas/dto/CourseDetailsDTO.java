package lk.ijse.cas.dto;

import java.io.Serializable;

import lk.ijse.cas.embedded.CourseDetailsPK;
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
        courseDetailsEntity.setCourseDetailsPK(new CourseDetailsPK(this.studentId, this.courseId));
        courseDetailsEntity.setCourse(null);
        courseDetailsEntity.setStudent(null);

        return courseDetailsEntity;
    }
}