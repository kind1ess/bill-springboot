package top.kindless.billtest.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowReserveAmount {

    private Integer goodsId;

    private Long sumAmount;
}
