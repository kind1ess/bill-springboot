package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.dto.OrderPreviewDto;
import top.kindless.billtest.model.params.OrderParams;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.OrderPreviewVo;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.annotation.UserAuth;
import top.kindless.billtest.security.roleenum.Role;
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
    @UserAuth
    public Result<String> addToOrder(@RequestBody OrderParams orderParams){
        List<Integer> cartIds = orderParams.getCartIds();
        String billOrderId = orderService.getBillOrderIdByCartIds(cartIds);
        return Result.ok(HttpStatus.OK.getReasonPhrase(),billOrderId);
    }

    @GetMapping("/findOrderVo/{billId}")
    @ApiOperation("根据订单编号查询订单信息")
    public Result<OrderVo> findOrderVo(@PathVariable String billId){
        OrderVo orderVo = orderService.findOrderVoByBillId(billId);
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderVo);
    }

    @GetMapping("/findOrderTitleVo")
    @ApiOperation("获取当前用户的所有订单预览信息")
    @UserAuth
    public Result<OrderPreviewVo> findOrderTitleVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.findOrderTitlesByUserId());
    }

    @GetMapping("/findAllOrderTitleVo/{page}/{size}")
    @ApiOperation("获取所有订单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN})
    public Result<OrderPreviewVo> findAllOrderTitleVo(@PathVariable("page") @ApiParam("页码") Integer page,@PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.findAllOrderTitleVo(page,size));
    }

    @GetMapping("/findAllOrderTitleVo")
    @ApiOperation("获取所有订单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN})
    public Result<OrderPreviewVo> findAllOrderTitleVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.findAllOrderTitleVo());
    }

    @PostMapping("/verifyOrder")
    @ApiOperation("销售员审核订单")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN})
    @SysLog("审核订单")
    public Result<Object> verifyOrder(@RequestBody VerifyParams verifyParams){
        orderService.verifyOrder(verifyParams);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/findOrderPreviewVoByStatusId/{statusId}/{page}/{size}")
    @ApiOperation("根据状态查询订单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<OrderPreviewVo> findOrderPreviewVoByStatusId(@PathVariable("statusId") Integer statusId,
                                                               @PathVariable("page") Integer page,
                                                               @PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.findOrderPreviewVoByStatusId(statusId,page,size));
    }
    

    @GetMapping("/findOrderPreviewVoByStatusId/{statusId}")
    @ApiOperation("根据状态查询订单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<OrderPreviewVo> findOrderPreviewVoByStatusId(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.findOrderPreviewVoByStatusId(statusId));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取订货单数量")
    @RoleAuth({Role.SALESPERSON,Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取订货单数量")
    @RoleAuth({Role.SALESPERSON,Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<Long> getCount(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),orderService.getCount(statusId));
    }
}
