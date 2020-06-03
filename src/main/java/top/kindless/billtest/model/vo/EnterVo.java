package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.dto.EnterDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterVo {

    @ApiModelProperty("入库单表头")
    private EnterDto enterDto;

    @ApiModelProperty("入库单明细")
    private List<ListGoodsWithoutPrice> listGoods;
}
