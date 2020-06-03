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
public class InvoiceDto extends AdminBillTitle {

    @ApiModelProperty("关联发货单编号")
    private String odoId;

    @ApiModelProperty("关联订单编号")
    private String orderId;

    @ApiModelProperty("用户收货地址")
    private String userAddress;

    @ApiModelProperty("用户电话")
    private String userTelephone;

    public InvoiceDto(String billId,
                      Date createTime,
                      Integer statusId,
                      String statusName,
                      Date updateTime,
                      String staffId,
                      String staffName,
                      String staffTelephone,
                      String odoId,
                      String orderId,
                      String userAddress,
                      String userTelephone){
        super(billId, createTime, statusId, statusName, updateTime, staffId, staffName, staffTelephone);
        this.odoId = odoId;
        this.orderId = orderId;
        this.userAddress = userAddress;
        this.userTelephone = userTelephone;
    }
}
