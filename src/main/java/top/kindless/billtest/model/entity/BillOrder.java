package top.kindless.billtest.model.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity(name = "bill_order")
@EntityListeners(AuditingEntityListener.class)
public class BillOrder {

    @Id
    private String id;

    @CreatedDate
    private Date createTime;

    private String userId;

    private Integer statusId;
}
