package top.kindless.billtest.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "detail_purchase")
public class DetailPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String billId;

    private String shortageId;

    private Integer goodsId;

    private Integer amount;
}
