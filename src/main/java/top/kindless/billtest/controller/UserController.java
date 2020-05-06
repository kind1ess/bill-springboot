package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.service.UserService;
import top.kindless.billtest.utils.Result;
import top.kindless.billtest.utils.UUIDUtils;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    @ApiOperation("用户注册")
    public Result<Object> signUp(@RequestBody User user){
//        System.out.println(user);
        user.setId(UUIDUtils.generateUserUUID());
        user.setCreditScore(80);
        userService.signUp(user);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<AuthToken> login(@RequestBody LoginParams loginParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),userService.login(loginParams));
    }

    @PostMapping("/logout")
    @ApiOperation("用户注销")
    public Result<Object> logout(){
        userService.logout();
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

}
