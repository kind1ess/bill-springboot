package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.dto.GoodsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo {
    private List<GoodsDto> goodsDtoList;
}
