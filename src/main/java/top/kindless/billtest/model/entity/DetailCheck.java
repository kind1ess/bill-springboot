package top.kindless.billtest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "detail_check")
@AllArgsConstructor
@NoArgsConstructor
public class DetailCheck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String billId;

    private String refBillId;

    private Integer goodsId;

    private Integer amount;

    private Integer actualAmount;

    private String remark;
}
