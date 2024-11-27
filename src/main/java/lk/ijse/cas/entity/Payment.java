package lk.ijse.cas.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lk.ijse.cas.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment implements Serializable {
    @Id
    @Column(name = "paymentId", length = 100)
    private String paymentId;
    @Column(name = "date", length = 100)
    private String date;
    @Column(name = "method", length = 100)
    private String method;
    @Column(name = "type", length = 100)
    private String type;
    @Column(name = "upfrontPayment", length = 100)
    private String upfrontPayment;
    @Column(name = "totalPayment", length = 100)
    private String  totalPayment;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

//    @ManyToMany
//    @JoinTable(name = "paymentDetails",
//            joinColumns = @JoinColumn(name = "paymentId"),
//            inverseJoinColumns = @JoinColumn(name = "programId"))
//    private List<Course> courses;

    public PaymentDTO toDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPId(this.paymentId);
        paymentDTO.setDate(this.date);
        paymentDTO.setMethod(this.method);
        paymentDTO.setType(this.type);
        paymentDTO.setUpfrontP(this.upfrontPayment);
        paymentDTO.setTotalP(this.totalPayment);
        paymentDTO.setSId(this.student.getStudentId());
        paymentDTO.setCp(null);
        return paymentDTO;
    }
}