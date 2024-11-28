package lk.ijse.cas.dto;

import java.io.Serializable;

import javafx.collections.ObservableList;
import lk.ijse.cas.entity.Payment;
import lk.ijse.cas.entity.Student;
import lk.ijse.cas.view.tdm.CoursePriceTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTO implements Serializable {
    private String pId;
    private String date;
    private String method;
    private String type;
    private String upfrontP;
    private String totalP;
    private String sId;
    private ObservableList<CoursePriceTm> cp;

    public Payment toEntity() {
        Payment paymentEntity = new Payment();
        paymentEntity.setPaymentId(this.pId);
        paymentEntity.setDate(this.date);
        paymentEntity.setMethod(this.method);
        paymentEntity.setType(this.type);
        paymentEntity.setUpfrontPayment((this.upfrontP == null) ? "_" : this.upfrontP);
        paymentEntity.setTotalPayment(this.totalP);
        paymentEntity.setStudent(null);

        Student student = new Student();
        student.setStudentId(this.sId);
        paymentEntity.setStudent(student);

        return paymentEntity;
    }
}