package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.params.PurchaseParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.PurchaseVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.PurchaseService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;


    @PostMapping("/generatePurchase")
    @ApiOperation("生成采购单")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN})
    public Result<String> generatePurchase(@RequestBody PurchaseParams purchaseParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.generatePurchase(purchaseParams));
    }

    @GetMapping("/findPurchaseVoById/{id}")
    @ApiOperation("根据id查询")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<PurchaseVo> findPurchaseVoById(@PathVariable String id){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.findPurchaseVoById(id));
    }

    @GetMapping("/findAllPurchasePreview")
    @ApiOperation("查询所有采购单预览信息")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllPurchasePreview(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.findAllPurchasePreview());
    }

    @GetMapping("/findAllPurchasePreview/{page}/{size}")
    @ApiOperation("查询所有采购单预览信息")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllPurchasePreview(@PathVariable Integer page,@PathVariable Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.findAllPurchasePreview(page, size));
    }

    @GetMapping("/findAllPurchasePreview/{statusId}")
    @ApiOperation("查询所有采购单预览信息")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<CommonBillPreviewVo> findAllPurchasePreview(@PathVariable Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.findAllPurchasePreview(statusId));
    }

    @GetMapping("/findAllPurchasePreview/{statusId}/{page}/{size}")
    @ApiOperation("查询所有采购单预览信息")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN,Role.INSPECTOR})
    public Result<CommonBillPreviewVo> findAllPurchasePreview(@PathVariable Integer statusId,
                                                              @PathVariable Integer page,
                                                              @PathVariable Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.findAllPurchasePreview(statusId,page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.BUYER,Role.SUPER_ADMIN})
    public Result<Long> getCount(@PathVariable Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),purchaseService.getCount(statusId));
    }
}
