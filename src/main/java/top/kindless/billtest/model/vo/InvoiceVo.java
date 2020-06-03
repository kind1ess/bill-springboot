package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.dto.InvoiceDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceVo {

    @ApiModelProperty("发货单表头")
    private InvoiceDto invoiceDto;

    @ApiModelProperty("单据明细")
    private List<CommonListGoods> commonListGoods;
}
