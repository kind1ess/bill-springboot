package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.ReturnPreviewVo;
import top.kindless.billtest.model.vo.ReturnVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.annotation.UserAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.ReturnService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/return")
public class ReturnController {

    @Autowired
    ReturnService returnService;

    @PostMapping("/generateBillReturn/{orderId}")
    @ApiOperation("根据已发货订货单生成还车单")
    @UserAuth
    public Result<String> generateBillReturn(@PathVariable("orderId") @ApiParam("订货单id") String orderId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.generateBillReturn(orderId));
    }

    @GetMapping("/findReturnVoById/{id}")
    @ApiOperation("根据还车单id查询还车单信息")
    public Result<ReturnVo> findReturnVoById(@PathVariable("id") String id){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findReturnVoById(id));
    }

    @GetMapping("/findAllReturnPreviewVo")
    @ApiOperation("获取所有还车单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<ReturnPreviewVo> findAllReturnPreviewVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findAllReturnPreview());
    }

    @GetMapping("/findAllReturnPreviewVo/{page}/{size}")
    @ApiOperation("获取所有还车单预览信息")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<ReturnPreviewVo> findAllReturnPreviewVo(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findAllReturnPreviewPageBy(page,size));
    }

    @GetMapping("/findReturnPreviewVoByStatusId/{statusId}")
    @ApiOperation("根据状态查询")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<ReturnPreviewVo> findReturnPreviewVoByStatusId(@ApiParam("状态id") @PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findAllReturnPreview(statusId));
    }

    @GetMapping("/findReturnPreviewVoByStatusId/{statusId}/{page}/{size}")
    @ApiOperation("根据状态查询")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<ReturnPreviewVo> findReturnPreviewVoByStatusId(
            @ApiParam("状态id") @PathVariable("statusId") Integer statusId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findAllReturnPreview(statusId,page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<Long> getCount(){
        return  Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<Long> getCount(@PathVariable Integer statusId){
        return  Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.getCount(statusId));
    }

    @PutMapping("/verifyBillReturn")
    @ApiOperation("审核还车单")
    @RoleAuth({Role.SALESPERSON,Role.SUPER_ADMIN})
    public Result<Object> verifyBillReturn(@RequestBody VerifyParams verifyParams){
        returnService.verify(verifyParams);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/findReturnPreviewVoByUserId")
    @ApiOperation("获取用户所有还车单")
    @UserAuth
    public Result<ReturnPreviewVo> findReturnPreviewVoByUserId(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),returnService.findAllReturnPreviewByUserId());
    }
}
