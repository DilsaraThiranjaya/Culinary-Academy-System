package lk.ijse.cas.entity;

import lk.ijse.cas.dto.CourseDetailsDTO;
import lk.ijse.cas.embedded.CourseDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programDetails")
public class CourseDetails implements Serializable {
    @EmbeddedId
    private CourseDetailsPK courseDetailsPK;
    @Column(name = "status", length = 100)
    private String status;

    @ManyToOne
    @JoinColumn(name = "studentId",
            insertable = false,
            updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "programId",
            insertable = false,
            updatable = false)
    private Course course;

    public CourseDetailsDTO toDTO() {
        CourseDetailsDTO courseDetailsDTO = new CourseDetailsDTO();
        courseDetailsDTO.setStudentId(this.courseDetailsPK.getStudentId());
        courseDetailsDTO.setCourseId(this.courseDetailsPK.getProgramId());
        courseDetailsDTO.setStatus(this.status);
        return courseDetailsDTO;
    }
}