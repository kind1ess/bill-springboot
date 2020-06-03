package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.AdminBillTitle;
import top.kindless.billtest.model.common.CommonListGoods;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortageVo {

    private AdminBillTitle adminBillTitle;

    private List<CommonListGoods> listGoods;
}
