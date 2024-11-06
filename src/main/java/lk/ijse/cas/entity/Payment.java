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
    @Column(name = "paymentId")
    private String paymentId;
    @Column(name = "date")
    private Date date;
    @Column(name = "method")
    private String method;
    @Column(name = "type")
    private String type;
    @Column(name = "upfrontPayment")
    private BigDecimal upfrontPayment;
    @Column(name = "totalPayment")
    private BigDecimal totalPayment;

    @OneToOne
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
        return paymentDTO;
    }
}