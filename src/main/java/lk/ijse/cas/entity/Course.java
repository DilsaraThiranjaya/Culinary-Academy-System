package lk.ijse.cas.entity;

import lk.ijse.cas.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programs")
public class Course implements Serializable {
    @Id
    @Column(name = "programId")
    private String programId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "duration")
    private String duration;
    @Column(name = "fee")
    private BigDecimal fee;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseDetails> courseDetails = new ArrayList<>();

    public CourseDTO toDTO() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(this.programId);
        courseDTO.setName(this.name);
        courseDTO.setDescription(this.description);
        courseDTO.setDuration(this.duration);
        courseDTO.setPrice(this.fee);
        return courseDTO;
    }
}
