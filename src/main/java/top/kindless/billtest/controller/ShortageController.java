package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.params.ShortageParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.ShortageVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.ShortageService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/shortage")
public class ShortageController {

    @Autowired
    ShortageService shortageService;

    @PostMapping("/generateShortage")
    @ApiOperation("生成缺货单")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    @SysLog("生成缺货单")
    public Result<String> generateShortage(@RequestBody ShortageParams shortageParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.generateBillShortage(shortageParams));
    }

    @GetMapping("/findShortageVoById/{id}")
    @ApiOperation("通过id查询缺货单")
    @RoleAuth({Role.STORE_MAN,Role.BUYER,Role.SUPER_ADMIN})
    public Result<ShortageVo> findShortageVoById(@PathVariable @ApiParam("缺货单id") String id){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.findShortageVoById(id));
    }

    @GetMapping("/findAllShortagePreviewVo")
    @ApiOperation("获取所有缺货单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllShortagePreviewVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.findAllShortagePreviewVo());
    }

    @GetMapping("/findAllShortagePreviewVo/{page}/{size}")
    @ApiOperation("获取所有缺货单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllShortagePreviewVo(@PathVariable Integer page,@PathVariable Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.findAllShortagePreviewVo(page, size));
    }

    @GetMapping("/findAllShortagePreviewVo/{statusId}")
    @ApiOperation("获取所有缺货单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.BUYER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllShortagePreviewVo(@PathVariable Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.findAllShortagePreviewVo(statusId));
    }

    @GetMapping("/findAllShortagePreviewVo/{statusId}/{page}/{size}")
    @ApiOperation("获取所有缺货单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.BUYER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllShortagePreviewVo(@PathVariable Integer statusId,
                                                                @PathVariable Integer page,
                                                                @PathVariable Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.findAllShortagePreviewVo(statusId, page, size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.STORE_MAN,Role.BUYER,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.STORE_MAN,Role.BUYER,Role.SUPER_ADMIN})
    public Result<Long> getCount(@PathVariable Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),shortageService.getCount(statusId));
    }
}
