package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.dto.ReturnDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVo {

    private ReturnDto returnDto;

    private List<CommonListGoods> listGoods;
}
