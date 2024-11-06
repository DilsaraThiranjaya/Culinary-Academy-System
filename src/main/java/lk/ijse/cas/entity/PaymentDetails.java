package lk.ijse.cas.entity;

import lk.ijse.cas.embedded.PaymentDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paymentDetails")
public class PaymentDetails implements Serializable {
    @EmbeddedId
    private PaymentDetailsPK paymentDetailsPK;
    @ManyToOne
    @JoinColumn(name = "paymentId",
            insertable = false,
            updatable = false)
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "programId",
            insertable = false,
            updatable = false)
    private String programId;
}