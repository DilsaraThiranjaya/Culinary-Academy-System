package lk.ijse.cas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StudentDTO {
    private String id;
    private String fname;
    private String lname;
    private String dOb;
    private String gender;
    private String admissionDate;
    private String nIC;
    private String address;
    private String cNo;
    private String email;
}
