package top.kindless.billtest.security.context;

import org.springframework.lang.Nullable;
import top.kindless.billtest.security.auth.Authentication;

/**
 * 验证信息上下文，主要用于保存验证信息
 */
public interface AuthContext {

    /**
     * 获取当前验证用户信息
     * @return
     */
    @Nullable
    Authentication getAuthentication();

    /**
     * 设置验证后的用户信息，可以是员工也可以是用户
     * @param authentication
     */
    void setAuthentication(@Nullable Authentication authentication);

    /**
     * 判断当前是否验证，默认为否
     * @return
     */
    default boolean isAuthenticated() {
        return getAuthentication() != null;
    }
}
