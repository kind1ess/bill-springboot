package top.kindless.billtest.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortageParam {

    @ApiModelProperty("货物id")
    private Integer goodsId;

    @ApiModelProperty("缺货数量")
    private Integer amount;
}
