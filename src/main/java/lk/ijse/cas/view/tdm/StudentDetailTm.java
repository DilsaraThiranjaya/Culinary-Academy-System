package lk.ijse.cas.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StudentDetailTm extends StudentTm {
    private String courseId;
    private String name;
    private String status;
}
