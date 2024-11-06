package lk.ijse.cas.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsPK implements Serializable{
    @Column(name = "studentId")
    private String studentId;
    @Column(name = "programId")
    private String programId;
}