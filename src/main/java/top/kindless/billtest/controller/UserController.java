package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.UserVo;
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

    @GetMapping("/getUserProfile")
    @ApiOperation("获取用户信息")
    public Result<UserVo> getUserProfile(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),userService.getUserProfile());
    }

    @PutMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public Result<Object> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }
}
