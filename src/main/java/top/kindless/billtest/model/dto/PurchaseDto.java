package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.AdminBillTitle;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto extends AdminBillTitle {
    @ApiModelProperty("发货时间")
    private String sendTime;

    @ApiModelProperty("收货地址")
    private String address;

    public PurchaseDto(String billId,
                       Date createTime,
                       Integer statusId,
                       String statusName,
                       Date updateTime,
                       String staffId,
                       String staffName,
                       String staffTelephone,
                       String sendTime,
                       String address) {
        super(billId, createTime, statusId, statusName, updateTime, staffId, staffName, staffTelephone);
        this.sendTime = sendTime;
        this.address = address;
    }
}
