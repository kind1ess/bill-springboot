package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.dto.CartDto;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVo {

    /**
     * 返回给前端页面的购物车数据
     */
    private List<CartDto> cartDtoList;

}
