package top.kindless.billtest.log.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SysLog {

    String value() default "";

    @AliasFor("value")
    String operation() default "";
}
