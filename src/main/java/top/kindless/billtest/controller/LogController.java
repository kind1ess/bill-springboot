package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kindless.billtest.model.entity.SysLogEntity;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.SysLogService;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    SysLogService sysLogService;

    @GetMapping("/pageBy/{page}/{size}")
    @ApiOperation("获取日志")
    @RoleAuth({Role.SUPER_ADMIN,Role.MANAGER})
    public Result<Page<SysLogEntity>> pageBy(@PathVariable Integer page, @PathVariable Integer size){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),sysLogService.listAll(page, size));
    }
}
