package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.PurchaseListGoods;
import top.kindless.billtest.model.dto.PurchaseDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseVo {

    private PurchaseDto purchaseDto;

    private List<PurchaseListGoods> listGoods;

}
