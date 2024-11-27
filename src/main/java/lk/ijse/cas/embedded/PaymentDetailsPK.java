package lk.ijse.cas.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsPK implements Serializable {
    @Column(name = "paymentId", length = 100)
    private String paymentId;
    @Column(name = "programId", length = 100)
    private String programId;
}
