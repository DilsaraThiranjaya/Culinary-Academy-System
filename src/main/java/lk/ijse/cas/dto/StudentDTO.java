package lk.ijse.cas.dto;

import java.io.Serializable;
import java.util.Date;

import lk.ijse.cas.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO implements Serializable {
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

    public Student toEntity() {
        Student studentEntity = new Student();
        studentEntity.setStudentId(this.id);
        studentEntity.setFirstName(this.fname);
        studentEntity.setLastName(this.lname);
        studentEntity.setDateOfBirth(this.dOb);
        studentEntity.setGender(this.gender);
        studentEntity.setAdmissionDate(this.admissionDate);
        studentEntity.setNIC(this.nIC);
        studentEntity.setAddress(this.address);
        studentEntity.setContactNo(this.cNo);
        studentEntity.setEmail(this.email);
        studentEntity.setPayment(null);
        studentEntity.setCourseDetails(null);
        return studentEntity;
    }
}