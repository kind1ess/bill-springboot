package top.kindless.billtest.security.context;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 用于维护当前线程的验证信息
 */
public class AuthContextHolder {

    //用threadLocal维护当前验证用户
    private final static ThreadLocal<AuthContext> CONTEXT_HOLDER = new ThreadLocal<>();

    //不允许外部实例化对象
    private AuthContextHolder(){
    }

    /**
     * 获得验证信息上下文
     * 如果当前没有验证信息上下文则创建一个空的验证信息上下文
     * @return authContext
     */
    @NonNull
    public static AuthContext getAuthContext(){
        AuthContext authContext = CONTEXT_HOLDER.get();
        if (authContext == null){
            authContext = createEmptyContext();
            setContext(authContext);
        }
        return authContext;
    }

    /**
     * 设置验证信息上下文
     * @param context context
     */
    public static void setContext(@Nullable AuthContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 清除验证信息上下文
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 创建一个空的验证信息上下文
     * @return AuthContextImpl
     */
    @NonNull
    private static AuthContext createEmptyContext() {
        return new AuthContextImpl(null);
    }
}
