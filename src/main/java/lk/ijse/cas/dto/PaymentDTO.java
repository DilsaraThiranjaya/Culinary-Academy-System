package lk.ijse.cas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lk.ijse.cas.entity.Payment;
import lk.ijse.cas.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTO implements Serializable {
    private String pId;
    private Date date;
    private String method;
    private String type;
    private BigDecimal upfrontP;
    private BigDecimal totalP;
    private String sId;

    public Payment toEntity() {
        Payment paymentEntity = new Payment();
        paymentEntity.setPaymentId(this.pId);
        paymentEntity.setDate(this.date);
        paymentEntity.setMethod(this.method);
        paymentEntity.setType(this.type);
        paymentEntity.setUpfrontPayment(this.upfrontP);
        paymentEntity.setTotalPayment(this.totalP);

        Student student = new Student();
        student.setStudentId(this.sId);
        paymentEntity.setStudent(student);

        return paymentEntity;
    }
}