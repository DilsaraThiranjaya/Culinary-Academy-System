package lk.ijse.cas.entity;

import lk.ijse.cas.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @Column(name = "studentId", length = 100)
    private String studentId;

    @Column(name = "firstName", length = 100)
    private String firstName;

    @Column(name = "lastName", length = 100)
    private String lastName;

    @Column(name = "dateOfBirth", length = 100)
    private String dateOfBirth;

    @Column(name = "gender", length = 100)
    private String gender;  // Removed @Enumerated annotation

    @Column(name = "admissionDate", length = 100)
    private String admissionDate;

    @Column(name = "NIC", length = 100)
    private String NIC;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "contactNo", length = 100)
    private String contactNo;

    @Column(name = "email", length = 100)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    private List<Payment> payment = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseDetails> courseDetails = new ArrayList<>();

    public StudentDTO toDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(this.studentId);
        studentDTO.setFname(this.firstName);
        studentDTO.setLname(this.lastName);
        studentDTO.setDOb(this.dateOfBirth);
        studentDTO.setGender(this.gender);
        studentDTO.setAdmissionDate(this.admissionDate);
        studentDTO.setNIC(this.NIC);
        studentDTO.setAddress(this.address);
        studentDTO.setCNo(this.contactNo);
        studentDTO.setEmail(this.email);
        return studentDTO;
    }
}