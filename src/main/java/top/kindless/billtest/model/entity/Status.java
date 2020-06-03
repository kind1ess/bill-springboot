package top.kindless.billtest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单据状态
 * 1.待审核
 * 2.未通过
 * 3.待出库
 * 4.待发货
 * 5.已发货
 * 6.待验收
 * 7.待入库
 * 8.已入库
 */
@Entity
@Table(name = "tb_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String statusName;

//    private String attribute;
}
