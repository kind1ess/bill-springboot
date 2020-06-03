package top.kindless.billtest.security.annotation;

import java.lang.annotation.*;

/**
 * 此注解的目的是验证一些需要登录才能访问的接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserAuth {
}
