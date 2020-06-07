package top.kindless.billtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.StaffPreviewVo;
import top.kindless.billtest.model.vo.StaffVo;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.service.AdminService;
import top.kindless.billtest.utils.Result;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public Result<AuthToken> adminLogin(@RequestBody LoginParams loginParams){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),adminService.adminLogin(loginParams));
    }

    @PostMapping("/adminLogout")
    @ApiOperation("管理员注销")
    @RoleAuth(Role.ALL_ADMIN)
    @SysLog("管理员注销")
    public Result<Object> adminLogout(){
        adminService.adminLogout();
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/addAdmin")
    @ApiOperation("添加管理员")
    @RoleAuth({Role.HR,Role.SUPER_ADMIN})
    @SysLog("添加管理员")
    public Result<Object> addAdmin(@RequestBody Staff staff){
        adminService.addAdmin(staff);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/getAdminProfile")
    @ApiOperation("获取管理员个人信息")
    @RoleAuth(Role.ALL_ADMIN)
    public Result<StaffVo> getAdminProfile(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),adminService.getStaffProfile());
    }

    @GetMapping("/getAllAdminPreview")
    @ApiOperation("获取所有管理员信息")
    @RoleAuth({Role.HR,Role.SUPER_ADMIN})
    @SysLog("获取所有管理员信息")
    public Result<StaffPreviewVo> getAllAdminPreview(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),adminService.findAllStaffPreview());
    }

    @DeleteMapping("/deleteAdminById/{staffId}")
    @ApiOperation("删除员工")
    @RoleAuth(Role.HR)
    @SysLog("删除员工")
    public Result<Object> deleteAdminById(@PathVariable String staffId){
        adminService.deleteStaffById(staffId);
        return Result.ok(HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/findAdminPreviewByDepartmentId/{departmentId}")
    @ApiOperation("根据部门查询员工")
    @RoleAuth({Role.HR,Role.SUPER_ADMIN})
    @SysLog("根据部门查询员工")
    public Result<StaffPreviewVo> findAdminPreviewByDepartmentId(@PathVariable("departmentId") Integer departmentId){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),adminService.findStaffPreviewByDepartmentId(departmentId));
    }

    @GetMapping("/findAllAdminVo")
    @ApiOperation("查询所有员工信息")
    @RoleAuth({Role.HR,Role.SUPER_ADMIN})
    @SysLog("查询所有员工信息")
    public Result<List<StaffVo>> findAllAdminVo(){
        return Result.ok(HttpStatus.OK.getReasonPhrase(),adminService.findAllStaffVo());
    }
}
