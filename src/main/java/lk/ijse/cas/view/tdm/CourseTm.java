package lk.ijse.cas.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CourseTm {
    private String id;
    private String name;
    private String description;
    private String duration;
    private String price;
}
