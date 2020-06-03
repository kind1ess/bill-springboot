package top.kindless.billtest.security.annotation;


import top.kindless.billtest.security.roleenum.Role;

import java.lang.annotation.*;

/**
 * 管理员权限验证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RoleAuth {

    /**
     * 0.所有管理员
     * 1.销售员
     * 2.仓库管理员
     * 3.发货员
     * 4.验收员
     * 5.采购员
     * @return
     */
    Role[] value();
}
