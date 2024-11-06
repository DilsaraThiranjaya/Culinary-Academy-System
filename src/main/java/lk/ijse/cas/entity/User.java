package lk.ijse.cas.entity;

import lk.ijse.cas.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "userId")
    private String userId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "position")
    private String position;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(this.userId);
        userDTO.setUserName(this.userName);
        userDTO.setPosition(this.position);
        userDTO.setEmail(this.email);
        return userDTO;
    }
}