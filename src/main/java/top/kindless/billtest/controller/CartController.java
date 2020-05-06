package top.kindless.billtest.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.entity.Cart;
import top.kindless.billtest.model.params.CartParams;
import top.kindless.billtest.model.vo.CartVo;
import top.kindless.billtest.service.CartService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    @ApiOperation("添加商品到购物车")
    public Result addToCart(@RequestBody CartParams cartParams){
        cartService.addToCart(cartParams);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/getCartList")
    @ApiOperation("获取用户的所有购物车信息")
    public Result<CartVo> getCartList(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),cartService.findAllCartByUserId());
    }
}
