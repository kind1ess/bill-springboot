package top.kindless.billtest.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "detail_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String billId;

    private Integer goodsId;

    private Integer amount;

    public DetailOrder(String billId,Integer goodsId,Integer amount){
        this.billId = billId;
        this.goodsId = goodsId;
        this.amount = amount;
    }
}
