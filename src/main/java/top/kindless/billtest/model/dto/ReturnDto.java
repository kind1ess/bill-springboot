package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.BillTitle;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDto extends BillTitle {

    @ApiModelProperty("关联订单编号")
    private String orderId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户电话")
    private String userTelephone;

    @ApiModelProperty("用户地址")
    private String userAddress;

    public ReturnDto(String billId,
                     Date createTime,
                     Integer statusId,
                     String statusName,
                     String orderId,
                     String userId,
                     String userAccount,
                     String userTelephone,
                     String userAddress){
        super(billId, createTime, statusId, statusName);
        this.orderId = orderId;
        this.userId = userId;
        this.userAccount = userAccount;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
    }
}
