package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.params.OrderParams;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.service.OrderService;
import top.kindless.billtest.utils.Result;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/addToOrder")
    @ApiOperation("将购物车里的商品添加到订单")
    public Result<OrderVo> addToOrder(@RequestBody OrderParams orderParams){
        List<Integer> cartIds = orderParams.getCartIds();
        OrderVo orderVo = orderService.getOrderVoByCartIds(cartIds);
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderVo);
    }

    @GetMapping("/findOrderVo/{billId}")
    @ApiOperation("根据订单编号查询订单信息")
    public Result<OrderVo> findOrderVo(@PathVariable String billId){
        OrderVo orderVo = orderService.findOrderVoByBillId(billId);
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderVo);
    }
}
