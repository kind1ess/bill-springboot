package top.kindless.billtest.security.auth;


import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.entity.User;

public interface Authentication {
    /**
     * 获取验证用户信息
     * @return
     */
    User getUser();

    /**
     * 获取验证员工信息
     * @return
     */
    Staff getStaff();

    /**
     * 获取token
     * @return
     */
    String getToken();
}
