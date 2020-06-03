package top.kindless.billtest.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bill_purchase")
@EntityListeners(AuditingEntityListener.class)
public class BillPurchase implements Serializable {

    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private String staffId;

    @ApiModelProperty(hidden = true)
    private Integer statusId;

    private String sendTime;

    private String address;
}
