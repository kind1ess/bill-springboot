package top.kindless.billtest.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_catalog")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsId;

    private Integer commodityId;

    private Integer specificationId;

    private Integer amount;
}
