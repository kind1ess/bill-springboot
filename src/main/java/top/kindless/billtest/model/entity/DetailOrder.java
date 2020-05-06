package top.kindless.billtest.model.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "detail_order")
@Data
public class DetailOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String billId;

    private Integer goodsId;

    private Integer amount;
}
