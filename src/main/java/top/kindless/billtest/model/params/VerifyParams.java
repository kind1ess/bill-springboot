package top.kindless.billtest.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 审核订单的参数
 */
@Data
public class  VerifyParams {

    @ApiModelProperty("订单编号")
    private String billId;

    @ApiModelProperty("状态id")
    private Integer statusId;
}
