package lk.ijse.cas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CourseDetailsDTO {
    private String courseId;
    private String studentId;
    private String status;
}
