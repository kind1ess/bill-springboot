package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 公共表头信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillTitle {

    @ApiModelProperty("单据编号")
    private String billId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("状态id")
    private Integer statusId;

    @ApiModelProperty("状态名称")
    private String statusName;
}
