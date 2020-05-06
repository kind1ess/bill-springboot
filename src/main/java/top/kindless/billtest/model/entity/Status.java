package top.kindless.billtest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 单据状态
 * 1.待审核
 * 2.未通过
 * 3.待出库
 * 4.待发货
 * 5.已发货
 * 6.待验收
 * 7.待入库
 * 8.已验收
 */
@Entity(name = "tb_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String statusName;
}
