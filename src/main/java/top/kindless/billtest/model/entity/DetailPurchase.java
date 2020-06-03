package top.kindless.billtest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "detail_purchase")
@AllArgsConstructor
@NoArgsConstructor
public class DetailPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String billId;

    private String shortageId;

    private Integer goodsId;

    private Integer amount;
}
