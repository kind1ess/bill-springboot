package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.StaffPreviewVo;
import top.kindless.billtest.model.vo.StaffVo;
import top.kindless.billtest.security.token.AuthToken;

import java.util.List;


public interface AdminService {

    /**
     * 管理员登录
     * @param loginParams
     * @return
     */
    @NonNull
    AuthToken adminLogin(@NonNull LoginParams loginParams);

    /**
     * 添加管理员
     * @param staff
     */
    void addAdmin(@NonNull Staff staff);

    /**
     * 获取管理员个人信息
     * @return
     */
    StaffVo getStaffProfile();

    /**
     * 通过id查询员工信息
     * @return
     */
    Staff findStaffById(String id);

    /**
     * 查询所有员工预览信息，包括id、姓名和部门名称
     * @return
     */
    StaffPreviewVo findAllStaffPreview();

    /**
     * 管理员注销
     */
    void adminLogout();

    /**
     * 删除员工
     * @param id 员工id
     */
    @Transactional
    void deleteStaffById(@NonNull String id);

    /**
     * 根据部门查询管理员预览信息
     * @param departmentId 部门id
     * @return 员工预览信息
     */
    StaffPreviewVo findStaffPreviewByDepartmentId(@NonNull Integer departmentId);

    /**
     * 查询所有员工信息
     * @return 所有员工信息
     */
    List<StaffVo> findAllStaffVo();
}
