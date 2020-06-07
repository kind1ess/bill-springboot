package top.kindless.billtest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.EnterVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.EnterService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/enter")
public class EnterController {

    @Autowired
    EnterService enterService;

    @PostMapping("/generateBillEnter")
    @ApiOperation("生成入库单")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    @SysLog("生成入库单")
    public Result<String> generateBillEnter(@ApiParam("验收单编号") @RequestBody BillParams billParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),enterService.generateEnter(billParams));
    }

    @GetMapping("/findEnterVoById/{id}")
    @ApiOperation("根据入库单id查询入库单详情")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<EnterVo> findEnterVoById(@PathVariable("id") String id){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),enterService.findEnterVoById(id));
    }

    @GetMapping("/findAllEnterPreview")
    @ApiOperation("查询所有入库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllEnterPreview(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),enterService.findAllEnterPreview());
    }

    @GetMapping("/findAllEnterPreview/{page}/{size}")
    @ApiOperation("查询所有入库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllEnterPreview(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),enterService.findAllEnterPreviewPageBy(page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),enterService.getCount());
    }
}
