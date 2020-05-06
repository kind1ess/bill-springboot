package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.entity.BillOrder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    @ApiModelProperty("单据表头信息")
    private OrderDto orderDto;

    @ApiModelProperty("单据货物详情信息")
    private List<ListGoods> goodsVoList;
}
