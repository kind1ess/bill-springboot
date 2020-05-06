package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.BillTitle;

/**
 * 订单表头
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends BillTitle {

    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户手机")
    private String userTelephone;

    @ApiModelProperty("用户地址")
    private String userAddress;
}
