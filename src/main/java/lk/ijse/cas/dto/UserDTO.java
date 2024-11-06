package lk.ijse.cas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserDTO {
    private String userId;
    private String userName;
    private String position;
    private String email;
    private String password;
    private String employeeId;
}
