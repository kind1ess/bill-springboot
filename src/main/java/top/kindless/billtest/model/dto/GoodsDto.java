package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.kindless.billtest.model.common.CommonGoodsInfo;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsDto extends CommonGoodsInfo {
    @ApiModelProperty("商品库存")
    private Integer amount;
}
