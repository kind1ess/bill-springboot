package top.kindless.billtest.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_commodity")
@Data
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String commodityName;

    private Float price;

    private String imgUrl;
}
