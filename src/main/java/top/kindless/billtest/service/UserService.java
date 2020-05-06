package top.kindless.billtest.service;

import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.security.token.AuthToken;

public interface UserService {

    /**
     * 注册
     * @param user
     */
    void signUp(User user);

    /**
     * 登录，返回给前端token
     * @param loginParams
     * @return
     */
    AuthToken login(LoginParams loginParams);

    User findUserById(String id);

    void logout();
}
