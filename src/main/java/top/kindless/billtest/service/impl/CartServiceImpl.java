package top.kindless.billtest.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.*;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.params.CartParams;
import top.kindless.billtest.model.vo.CartVo;
import top.kindless.billtest.repository.*;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@CacheConfig(cacheNames = "cart")
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final InventoryService inventoryService;
    private final FdInventoryService fdInventoryService;

    public CartServiceImpl(CartRepository cartRepository,
                           InventoryService inventoryService,
                           FdInventoryService fdInventoryService) {
        this.cartRepository = cartRepository;
        this.inventoryService = inventoryService;
        this.fdInventoryService = fdInventoryService;
    }


    @Override
    public void addToCart(CartParams cartParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String userId = authentication.getUser().getId();
        Cart waitToAdd = new Cart();
        waitToAdd.setUserId(userId);
        Integer goodsId = cartParams.getGoodsId();
        try {
            inventoryService.findById(goodsId);
        } catch (Exception e) {
            throw new BadRequestException("您在逗我？");
        }
        Integer amount = cartParams.getAmount();
        waitToAdd.setGoodsId(goodsId);
        waitToAdd.setAmount(amount);
        String message = "购物车里该商品数量超过限制";
        if (amount > 10){
            throw new AmountOutOfBoundException(message);
        }
        Cart cart = cartRepository.findByUserIdAndGoodsId(userId, goodsId);
        if (cart==null)
            saveCart(waitToAdd);
        else if (cart.getAmount()+waitToAdd.getAmount()>10){
            throw new AmountOutOfBoundException(message);
        }else {
            cart.setAmount(cart.getAmount()+waitToAdd.getAmount());
            updateCart(cart);
        }
    }

    @Override
    public CartVo findAllCartByUserId() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        User user = authentication.getUser();
        List<CartDto> cartDtoList = findAllCartDtoByUserId(user.getId());
        return new CartVo(cartDtoList);
    }

    @Override
    public CartVo findAllCartByIds(List<Integer> ids) {
        return new CartVo(findAllCartDtoByIds(ids));
    }

    @Override
    @Cacheable(keyGenerator = "myKeyGenerator")
    public String findUserIdById(Integer id) {
        return cartRepository.findUserIdById(id);
    }

    @Override
    public List<ListGoods> convertCartDtoListToGoodsList(List<CartDto> cartDtoList) {
        return cartDtoList
                .stream()
                .map(s -> {
                    Integer cartId = s.getCartId();
                    Integer goodsId = s.getGoodsId();
                    String commodityName = s.getCommodityName();
                    String specificationName = s.getSpecificationName();
                    Integer amount = s.getAmount();
                    Float price = s.getPrice();
                    Float sumPrice = s.getSumPrice();
                    return new ListGoods(cartId, goodsId, commodityName, specificationName, amount, price, sumPrice);
                }).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(key = "#cartId")
    public void deleteCartById(Integer cartId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        if (!authentication.getUser().getId().equals(findUserIdById(cartId))){
            throw new ForbiddenException("禁止删除其他用户的购物车");
        }
        if (isExist(cartId))
            cartRepository.deleteById(cartId);
    }

    @Override
    public void deleteCartsByIds(List<Integer> ids) {
        for (Integer id : ids) {
            deleteCartById(id);
        }
    }

    @Override
    @Cacheable(key = "#id")
    public Cart findCartById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String userId = authentication.getUser().getId();
        Optional<Cart> cartOptional = cartRepository.findById(id);
        Cart cart;
        if (cartOptional.isPresent()){
            cart = cartOptional.get();
            if (!userId.equals(cart.getUserId())){
                throw new ForbiddenException("禁止访问其他用户的购物车").setErrorData(cart.getId());
            }
        }else {
            throw new BadRequestException("不存在id为"+id+"的购物车").setErrorData(id);
        }
        if (cart.getAmount()>fdInventoryService.findAmountById(cart.getGoodsId())){
            throw new InventoryShortageException("库存不足，购物车无法提交").setErrorData(id);
        }
        return cart;
    }

    @Override
    @CachePut(key = "#cart.id")
    public Cart updateCart(Cart cart) {
        return cartRepository.saveAndFlush(cart);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartBySelect(CartVo cartVo) {
        List<CartDto> cartDtoList = cartVo.getCartDtoList();
        List<Integer> ids = new ArrayList<>();
        for (CartDto cartDto : cartDtoList) {
            Integer cartId = cartDto.getCartId();
            ids.add(cartId);
        }
        deleteCartsByIds(ids);
    }

    /**
     * 根据id判断该购物车是否存在
     * @param cartId 购物车id
     * @return 如果存在则返回true
     */
    private boolean isExist(Integer cartId){
        return cartRepository.existsById(cartId);
    }

    /**
     * 根据用户id查询购物车信息
     * @param userId 用户id
     * @return 购物车信息
     */
    private List<CartDto> findAllCartDtoByUserId(@NonNull String userId){
        return cartRepository.findAllCartDtoByUserId(userId);
    }

    /**
     * 根据购物车id查询购物车信息集合
     * @param ids 购物车id
     * @return 购物车信息集合
     */
    private List<CartDto> findAllCartDtoByIds(List<Integer> ids){
        List<CartDto> cartDtoList = new ArrayList<>();
        for (Integer id : ids) {
            CartDto cartDto = cartRepository.findCartDtoById(id);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }
}
