package top.kindless.billtest.model.params;

import lombok.Data;

import java.util.List;

/**
 * 用户将购物车加入到订单的参数，
 * 传入购物车每一列id，服务器拿到id获取购物车中用户id生成订单表头信息，
 * 根据购物车详情生成订单详情信息
 */
@Data
public class OrderParams {
    private List<Integer> cartIds;
}
