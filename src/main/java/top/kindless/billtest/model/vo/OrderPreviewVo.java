package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.dto.OrderPreviewDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPreviewVo {

    private List<OrderPreviewDto> orderPreviewDtoList;
}
