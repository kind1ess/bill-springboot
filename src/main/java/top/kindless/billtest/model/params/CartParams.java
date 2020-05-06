package top.kindless.billtest.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartParams {

    @ApiModelProperty("货物id")
    private Integer goodsId;

    @ApiModelProperty("货物数量")
    private Integer amount;
}
