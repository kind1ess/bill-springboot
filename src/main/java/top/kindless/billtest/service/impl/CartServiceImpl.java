package top.kindless.billtest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
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

@Service
@Slf4j
@CacheConfig(cacheNames = "cart")
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final InventoryService inventoryService;
    private final CommodityService commodityService;
    private final SpecificationService specificationService;
    private final FdInventoryService fdInventoryService;

    public CartServiceImpl(CartRepository cartRepository,
                           InventoryService inventoryService,
                           CommodityService commodityService,
                           SpecificationService specificationService,
                           FdInventoryService fdInventoryService) {
        this.cartRepository = cartRepository;
        this.inventoryService = inventoryService;
        this.commodityService = commodityService;
        this.specificationService = specificationService;
        this.fdInventoryService = fdInventoryService;
    }

    @Override
    public void addToCart(CartParams cartParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            //如果为空则未登录
            throw new UnAuthorizedException("未授权请先登录");
        }
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
        if (amount >10){
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
        if (authentication == null){
            //如果为空则未登录
            throw new UnAuthorizedException("未授权请先登录");
        }
        User user = authentication.getUser();
        System.out.println(user);
        List<Cart> carts = cartRepository.findAllByUserId(user.getId());
        return convertCartListToCartVo(carts);
    }

    @Override
    public CartVo findAllCartByIds(List<Integer> ids) {
        List<Cart> carts = new ArrayList<>();
        Cart cart;
        for (Integer id : ids) {
            cart = findCartById(id);
            carts.add(cart);
        }
        return convertCartListToCartVo(carts);
    }

    @Override
    @Cacheable(keyGenerator = "myKeyGenerator")
    public String findUserIdById(Integer id) {
        return cartRepository.findUserIdById(id);
    }

    @Override
    public List<ListGoods> convertCartDtoListToGoodsList(List<CartDto> cartDtoList) {
        List<ListGoods> goodsList = new ArrayList<>();
        ListGoods listGoods;
        for (CartDto cartDto : cartDtoList) {
            listGoods = convertCartDtoToListGoods(cartDto);
            goodsList.add(listGoods);
        }
        return goodsList;
    }

    @Override
    @CacheEvict(key = "#cartId")
    public void deleteCartById(Integer cartId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
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
        if (authentication == null){
            //如果为空则未登录
            throw new UnAuthorizedException("未授权请先登录");
        }
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
            throw new InventoryShortageException("库存不足，购物车无法提交").setErrorData(convertCartToCartDto(cart));
        }
        return cart;
    }

    @Override
    @CachePut(key = "#cart.id")
    public Cart updateCart(Cart cart) {
        cartRepository.saveAndFlush(cart);
        return cart;
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
        return findCartById(cartId)!=null;
    }
    /**
     * 将List<cart>转换为CartVo
     * @param carts 购物车信息集合
     * @return CartVo
     */
    private CartVo convertCartListToCartVo(List<Cart> carts){
        List<CartDto> cartDtoList = new ArrayList<>();
        CartDto cartDto;
        for (Cart cart : carts) {
            cartDto = convertCartToCartDto(cart);
            cartDtoList.add(cartDto);
        }
        return new CartVo(cartDtoList);
    }

    /**
     * 将Cart转换为CartDto
     * @param cart 购物车信息
     * @return CartDto
     */
    private CartDto convertCartToCartDto(Cart cart){
        Inventory inventory = inventoryService.findById(cart.getGoodsId());
        Commodity commodity = commodityService.findById(inventory.getCommodityId());
        Specification specification = specificationService.findById(inventory.getSpecificationId());
        Float price = commodity.getPrice();
        Integer amount = cart.getAmount();
        Float sumPrice = price*amount;
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setAmount(amount);
        cartDto.setSumPrice(sumPrice);
        cartDto.setCommodityId(commodity.getId());
        cartDto.setCommodityName(commodity.getCommodityName());
        cartDto.setGoodsId(inventory.getGoodsId());
        cartDto.setPrice(price);
        cartDto.setSpecificationId(specification.getId());
        cartDto.setSpecificationName(specification.getSpecificationName());
        cartDto.setImgUrl(commodity.getImgUrl());
        return cartDto;
    }

    /**
     * 将CartDto转换为ListGoods
     * @param cartDto CartDto
     * @return 单据展示的商品信息
     */
    private ListGoods convertCartDtoToListGoods(CartDto cartDto){
        Integer goodsId = cartDto.getGoodsId();
        String commodityName = cartDto.getCommodityName();
        String specificationName = cartDto.getSpecificationName();
        Float price = cartDto.getPrice();
        Integer amount = cartDto.getAmount();
        Float sumPrice = cartDto.getSumPrice();
        ListGoods listGoods = new ListGoods();
        listGoods.setGoodsId(goodsId);
        listGoods.setCommodityName(commodityName);
        listGoods.setSpecificationName(specificationName);
        listGoods.setPrice(price);
        listGoods.setAmount(amount);
        listGoods.setSumPrice(sumPrice);
        return listGoods;
    }
}
