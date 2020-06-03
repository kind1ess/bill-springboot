package top.kindless.billtest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.InvoiceVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.InvoiceService;
import top.kindless.billtest.utils.Result;

/**
 * 发货单api接口
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/generateInvoiceList")
    @ApiOperation("生成发货单")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> generateInvoiceList(@RequestBody BillParams billParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.generateInvoiceList(billParams));
    }

    @GetMapping("/findInvoiceVoById/{invoiceId}")
    @ApiOperation("根据id查询发货单")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<InvoiceVo> findInvoiceVoById(@PathVariable String invoiceId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.findInvoiceVoById(invoiceId));
    }

    @GetMapping("/findAllInvoicePreviewVo")
    @ApiOperation("查询所有发货单预览信息")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllInvoicePreviewVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.findAllInvoicePreviewVo());
    }

    @GetMapping("/findAllInvoicePreviewVo/{page}/{size}")
    @ApiOperation("查询所有发货单预览信息")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllInvoicePreviewVo(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.findAllInvoicePreviewVo(page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<Long> getCount(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),invoiceService.getCount(statusId));
    }
}
