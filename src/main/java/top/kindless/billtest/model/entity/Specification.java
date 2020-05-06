package top.kindless.billtest.model.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_specification")
@Data
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String specificationName;
}
