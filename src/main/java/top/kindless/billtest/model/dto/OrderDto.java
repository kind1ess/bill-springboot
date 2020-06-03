package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import top.kindless.billtest.model.common.BillTitle;

import java.util.Date;

/**
 * 订单表头
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class OrderDto extends BillTitle {

    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户手机")
    private String userTelephone;

    @ApiModelProperty("用户地址")
    private String userAddress;

    public OrderDto(String billId, Date createTime,Integer statusId,String statusName,String userId,String userAccount,String userTelephone,String userAddress){
        super(billId, createTime, statusId, statusName);
        this.userId = userId;
        this.userAccount = userAccount;
        this.userTelephone = userTelephone;
        this.userAddress = userAddress;
    }
}
