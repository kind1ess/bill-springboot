package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.UserVo;
import top.kindless.billtest.security.token.AuthToken;

public interface UserService {

    /**
     * 注册
     * @param user
     */
    @Transactional
    void signUp(@NonNull User user);

    /**
     * 登录，返回给前端token
     * @param loginParams must not be null
     * @return
     */
    AuthToken login(@NonNull LoginParams loginParams);

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    User findUserById(@NonNull String id);

    /**
     * 用户退出登录
     */
    void logout();

    /**
     * 获取用户信息
     * @return UserVo
     */
    UserVo getUserProfile();

    /**
     * 更新用户
     * @param user
     * @return
     */
    @NonNull
    @Transactional
    User updateUser(@NonNull User user);
}
