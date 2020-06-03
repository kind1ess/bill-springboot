package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CheckListGoods;
import top.kindless.billtest.model.dto.CheckDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVo {

    @ApiModelProperty("验收单表头")
    private CheckDto checkDto;

    @ApiModelProperty("验收单明细")
    private List<CheckListGoods> listGoods;
}
