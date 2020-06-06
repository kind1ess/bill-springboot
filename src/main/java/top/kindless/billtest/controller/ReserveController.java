package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kindless.billtest.model.common.AllReserveAmount;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.annotation.UserAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.ReserveService;
import top.kindless.billtest.utils.Result;

import java.util.List;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    @Autowired
    ReserveService reserveService;

    @GetMapping("/findAllCount")
    @ApiOperation("获取所有库存信息")
    @RoleAuth({Role.STORE_MAN,Role.SUPER_ADMIN})
    public Result<List<AllReserveAmount>> findAllCount(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),reserveService.countAllAmount());
    }

    @GetMapping("/findAllCountFromUser")
    @ApiOperation("获取所有库存信息")
    public Result<List<AllReserveAmount>> findAllCountFromUser(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),reserveService.countAllAmount());
    }
}
