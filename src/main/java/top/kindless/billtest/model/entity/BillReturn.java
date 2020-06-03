package top.kindless.billtest.model.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bill_return")
@EntityListeners(AuditingEntityListener.class)
public class BillReturn implements Serializable {

    @Id
    private String id;

    private Date createTime;

    private String orderId;

    private Integer statusId;
}
