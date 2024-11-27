package lk.ijse.cas.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lk.ijse.cas.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CourseDTO implements Serializable {
    private String id;
    private String name;
    private String description;
    private String duration;
    private String price;

    public Course toEntity() {
        Course courseEntity = new Course();
        courseEntity.setProgramId(this.id);
        courseEntity.setName(this.name);
        courseEntity.setDescription(this.description);
        courseEntity.setDuration(this.duration);
        courseEntity.setFee(this.price);
        courseEntity.setCourseDetails(null);
        return courseEntity;
    }
}
