package top.kindless.billtest.model.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bill_order")
public class BillOrder implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private Date createTime;

    private String userId;

    private Integer statusId;
}
