package top.kindless.billtest.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.params.CartParams;
import top.kindless.billtest.model.params.DeleteCartParams;
import top.kindless.billtest.model.vo.CartVo;
import top.kindless.billtest.service.CartService;
import top.kindless.billtest.utils.Result;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    @ApiOperation("添加商品到购物车")
    public Result<Object> addToCart(@RequestBody CartParams cartParams){
        cartService.addToCart(cartParams);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/getCartList")
    @ApiOperation("获取用户的所有购物车信息")
    public Result<CartVo> getCartList(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),cartService.findAllCartByUserId());
    }

    @DeleteMapping("/deleteCarts")
    @ApiOperation("删除选中的购物车")
    public Result<Object> deleteCarts(@RequestBody CartVo cartVo){
        cartService.deleteCartBySelect(cartVo);
        return  Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @DeleteMapping("/deleteCartsByIds")
    @ApiOperation("根据购物车id集合删除购物车")
    public Result<Object> deleteCartsByIds(@RequestBody DeleteCartParams deleteCartParams){
        cartService.deleteCartsByIds(deleteCartParams.getCartIdList());
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }
}
