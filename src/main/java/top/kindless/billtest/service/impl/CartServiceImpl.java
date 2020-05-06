package top.kindless.billtest.service.impl;

import lombok.extern.slf4j.Slf4j;
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
        String message = "购物车该商品数量超过限制";
        if (amount >10){
            throw new AmountOutOfBoundException(message);
        }
        Cart cart = cartRepository.findByUserIdAndGoodsId(userId, goodsId);
        if (cart==null)
            cartRepository.save(waitToAdd);
        else if (cart.getAmount()+waitToAdd.getAmount()>10){
            throw new AmountOutOfBoundException(message);
        }else {
            cart.setAmount(cart.getAmount()+waitToAdd.getAmount());
            cartRepository.save(cart);
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
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            //如果为空则未登录
            throw new UnAuthorizedException("未授权请先登录");
        }
        String userId = authentication.getUser().getId();
        List<Cart> carts = new ArrayList<>();
        Cart cart = null;
        for (Integer id : ids) {
            Optional<Cart> cartOptional = cartRepository.findById(id);
            if (cartOptional.isPresent()){
                cart = cartOptional.get();
                if (!userId.equals(cart.getUserId())){
                    throw new BadRequestException("创建订单失败，请不要拿别人购物车里的东西").setErrorData(convertCartToCartDto(cart));
                }
            }
            if (cart == null){
                throw new InternalServerErrorException("服务器错误,不存在id为"+id+"的购物车").setErrorData(id);
            }
            if (cart.getAmount()>fdInventoryService.findAmountById(cart.getGoodsId())){
                throw new InventoryShortageException("库存不足，不能提交订单").setErrorData(convertCartToCartDto(cart));
            }else {
                carts.add(cart);
            }
        }
        return convertCartListToCartVo(carts);
    }

    @Override
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

    /**
     * 将List<cart>转换为CartVo
     * @param carts
     * @return
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
     * @param cart
     * @return
     */
    private CartDto convertCartToCartDto(Cart cart){
        Inventory inventory = inventoryService.findById(cart.getGoodsId());
        Commodity commodity = commodityService.findById(inventory.getCommodityId());
        Specification specification = specificationService.findById(inventory.getSpecificationId());
        Float price = commodity.getPrice();
        Integer amount = cart.getAmount();
        Float sumPrice = price*amount;
        return new CartDto(
                cart.getId(),
                inventory.getGoodsId() ,
                commodity.getId(),
                specification.getId(),
                commodity.getCommodityName(),
                specification.getSpecificationName(),
                price,
                amount,
                sumPrice);
    }

    /**
     * 将CartDto转换为ListGoods
     * @param cartDto
     * @return
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
