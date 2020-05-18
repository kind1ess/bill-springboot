package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    void addToCart(@NonNull CartParams cartParams);

    /**
     * 寻找某一用户的所有购物车信息
     * @return
     */
    CartVo findAllCartByUserId();

    /**
     * 根据id集合查询所有购物车信息
     * @param ids cart must not be null
     * @return
     */
    CartVo findAllCartByIds(@NonNull List<Integer> ids);

    /**
     * 根据购物车id查询用户id
     * @param id cart must not be null
     * @return
     */
    @NonNull
    String findUserIdById(@NonNull Integer id);

    /**
     * 将CartDto转化为List<ListGoods>
     * @param cartDtoList cart must not be null
     * @return
     */
    @NonNull
    List<ListGoods> convertCartDtoListToGoodsList(@NonNull List<CartDto> cartDtoList);

    /**
     * 根据id删除购物车
     * @param cartId cart must not be null
     */
    @Transactional
    void deleteCartById(@NonNull Integer cartId);

    /**
     * 根据id删除所有购物车
     * @param ids cart must not be null
     */
    @Transactional
    void deleteCartsByIds(@NonNull List<Integer> ids);

    /**
     * 根据id查询
     * @param id cart must not be null
     * @return
     */
    Cart findCartById(@NonNull Integer id);

    /**
     *
     * @param cart must not be null
     * @return
     */
    @NonNull
    @Transactional
    Cart updateCart(@NonNull Cart cart);

    /**
     * 保存购物车
     * @param cart
     */
    void saveCart(@NonNull Cart cart);

    /**
     * 根据前端选择的购物车删除
     * @param cartVo
     */
    @Transactional
    void deleteCartBySelect(@NonNull CartVo cartVo);
}
