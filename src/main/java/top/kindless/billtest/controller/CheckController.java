package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CheckVo;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.CheckService;
import top.kindless.billtest.utils.Result;


@RestController
@RequestMapping("/api/check")
public class CheckController {

    @Autowired
    CheckService checkService;

    @PostMapping("/generateBillCheck")
    @ApiOperation("生成验收单")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN})
    @SysLog("生成验收单")
    public Result<String> generateBillCheck(@RequestBody @ApiParam("关联单据id集合") BillParams billParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.generateCheck(billParams));
    }

    @GetMapping("/findCheckVoById/{id}")
    @ApiOperation("根据单据编号查询验收单详情")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN,Role.STORE_MAN})
    public Result<CheckVo> findCheckVoById(@PathVariable("id") @ApiParam("验收单编号") String id){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.findCheckVoByBillId(id));
    }

    @GetMapping("/findAllCheckPreview")
    @ApiOperation("获取所有验收单预览信息")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllCheckPreview(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.findAllCheckPreview());
    }

    @GetMapping("/findAllCheckPreview/{page}/{size}")
    @ApiOperation("获取所有验收单预览信息")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllCheckPreview(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.findAllCheckPreviewPageBy(page,size));
    }

    @GetMapping("/findCheckPreviewByStatusId/{statusId}")
    @ApiOperation("根据状态获取验收单预览信息")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN,Role.STORE_MAN})
    public Result<CommonBillPreviewVo> findCheckPreviewByStatusId(@ApiParam("状态id") @PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.findAllCheckPreview(statusId));
    }

    @GetMapping("/findCheckPreviewByStatusId/{statusId}/{page}/{size}")
    @ApiOperation("根据状态获取验收单预览信息")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN,Role.STORE_MAN})
    public Result<CommonBillPreviewVo> findCheckPreviewByStatusId(
            @ApiParam("状态id") @PathVariable("statusId") Integer statusId,
            @ApiParam("页码") @PathVariable("page") Integer page,
            @PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.findAllCheckPreview(statusId,page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN,Role.STORE_MAN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.INSPECTOR,Role.SUPER_ADMIN,Role.STORE_MAN})
    public Result<Long> getCount(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),checkService.getCount(statusId));
    }
}
