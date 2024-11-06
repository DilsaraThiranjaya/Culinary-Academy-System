package lk.ijse.cas.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentTm {
    private String pId;
    private String date;
    private String sId;
    private String sName;
    private String courses;
    private String upfrontP;
    private String totalP;
    private String pMethod;
    private String pType;
}
