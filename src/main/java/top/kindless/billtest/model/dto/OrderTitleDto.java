package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTitleDto {

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("状态id")
    private Integer statusId;

    @ApiModelProperty("状态名称")
    private String statusName;
}
