package lk.ijse.cas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CourseDTO {
    private String id;
    private String name;
    private String description;
    private String duration;
    private String price;
}
