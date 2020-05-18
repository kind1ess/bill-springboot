package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.dto.OrderTitleDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTitleVo {

    private List<OrderTitleDto> orderTitleDtoList;
}
