package top.kindless.billtest.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "bill_return")
public class BillReturn {

    @Id
    private String id;

    private Date createTime;

    private String orderId;

    private Integer statusId;
}
