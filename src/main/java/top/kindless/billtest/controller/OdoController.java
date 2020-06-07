package top.kindless.billtest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.OdoVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.OdoService;
import top.kindless.billtest.utils.Result;

/**
 * 出库单接口
 */
@RestController
@RequestMapping("/api/odo")
public class OdoController {

    @Autowired
    OdoService odoService;

    @PostMapping("/addToOdo")
    @ApiOperation("将订货单添加到出库单")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    @SysLog("生成出库单")
    public Result<String> addToOdo(@RequestBody BillParams billParams) {
        return Result.ok(HttpStatus.OK.getReasonPhrase(), odoService.addToOdo(billParams));
    }

    @GetMapping("/findAllOdoPreviewVo")
    @ApiOperation("获取所有出库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllOdoPreviewVo() {
        return Result.ok(HttpStatus.OK.getReasonPhrase(), odoService.findAllOdoPreviewVo());
    }

    @GetMapping("/findAllOdoPreviewVo/{page}/{size}")
    @ApiOperation("获取所有出库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findAllOdoPreviewVo(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return Result.ok(HttpStatus.OK.getReasonPhrase(), odoService.findAllOdoPreviewVo(page,size));
    }

    @GetMapping("/findOdoVoById/{odoId}")
    @ApiOperation("根据出库单id查询出库单详情")
    @RoleAuth({Role.STORE_MAN, Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<OdoVo> findOdoVoById(@PathVariable String odoId) {
        return Result.ok(HttpStatus.OK.getReasonPhrase(), odoService.findOdoVoById(odoId));
    }

    @GetMapping("/findOdoPreviewByStatusId/{statusId}")
    @ApiOperation("根据状态id查询出库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findOdoPreviewByStatusId(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),odoService.findOdoPreviewVoByStatusId(statusId));
    }

    @GetMapping("/findOdoPreviewByStatusId/{statusId}/{page}/{size}")
    @ApiOperation("根据状态id查询出库单预览信息")
    @RoleAuth({Role.STORE_MAN,Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<CommonBillPreviewVo> findOdoPreviewByStatusId(
            @PathVariable("statusId") Integer statusId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),odoService.findOdoPreviewVoByStatusId(statusId,page,size));
    }

    @GetMapping("/getCount")
    @ApiOperation("获取数量")
    @RoleAuth({Role.STORE_MAN,Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<Long> getCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),odoService.getCount());
    }

    @GetMapping("/getCount/{statusId}")
    @ApiOperation("获取数量")
    @RoleAuth({Role.STORE_MAN,Role.SHIPPER,Role.SUPER_ADMIN})
    public Result<Long> getCount(@PathVariable("statusId") Integer statusId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),odoService.getCount(statusId));
    }
}
