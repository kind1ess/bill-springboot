package top.kindless.billtest.model.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.entity.BillPurchase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseParams {

    private BillPurchase billPurchase;

    private List<String> billIdList;
}
