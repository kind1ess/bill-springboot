package top.kindless.billtest.service;

import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.entity.Cart;
import top.kindless.billtest.model.params.CartParams;
import top.kindless.billtest.model.vo.CartVo;

import java.util.List;

public interface CartService {

    /**
     * 添加到购物车
     * @param cartParams
     */
    void addToCart(CartParams cartParams);

    /**
     * 寻找某一用户的所有购物车信息
     * @return
     */
    CartVo findAllCartByUserId();

    /**
     * 根据id集合查询所有购物车信息
     * @param ids
     * @return
     */
    CartVo findAllCartByIds(List<Integer> ids);

    /**
     * 根据购物车id查询用户id
     * @param id
     * @return
     */
    String findUserIdById(Integer id);

    /**
     * 将CartDto转化为List<ListGoods>
     * @param cartDtoList
     * @return
     */
    List<ListGoods> convertCartDtoListToGoodsList(List<CartDto> cartDtoList);
}
