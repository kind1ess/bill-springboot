package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.utils.Result;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public Result<AuthToken> adminLogin(@RequestBody LoginParams loginParams){
        return null;
    }

    @PostMapping("/addAdmin")
    @ApiOperation("添加管理员")
    public Result<Object> addAdmin(@RequestBody Staff staff){
        return null;
    }
}
