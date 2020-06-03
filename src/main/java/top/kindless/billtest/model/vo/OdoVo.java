package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.dto.OdoDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OdoVo {

    @ApiModelProperty("出库单表头")
    private OdoDto odoDto;

    @ApiModelProperty("出库单明细")
    private List<ListGoodsWithoutPrice> goodsWithoutPriceList;
}
