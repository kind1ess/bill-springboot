package top.kindless.billtest.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.kindless.billtest.exception.ForbiddenException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.security.annotation.RoleAuth;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.roleenum.Role;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class RoleAuthInterceptor {

    @Pointcut("@annotation(top.kindless.billtest.security.annotation.RoleAuth)")
    public void pointcut(){

    }

    /**
     * 验证角色权限
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object authRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        log.info(methodName+"开始执行");
        RoleAuth roleAuth = methodSignature.getMethod().getAnnotation(RoleAuth.class);
//        int value = roleAuth.value().getValue();
        Role[] roles = roleAuth.value();
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        try {
            String message = "未授权请先登录";
            if (authentication == null){
                throw new UnAuthorizedException(message);
            }
            Staff staff = authentication.getStaff();
            if (staff == null){
                log.error("员工未登录");
                throw new UnAuthorizedException(message);
            }
//            log.info(staff.toString());
            Integer departmentId = staff.getDepartmentId();
            if (!doAuth(roles,departmentId)){
                throw new ForbiddenException("您的权限不足，无法访问");
            }
            Object[] args = joinPoint.getArgs();
            return joinPoint.proceed(args);
        }finally {
            log.info(methodName+"执行完毕");
        }
    }

    private boolean doAuth(Role[] roles,Integer departmentId){
        int value;
        for (Role role : roles) {
            value = role.getValue();
            if (departmentId == value||departmentId.equals(Role.MANAGER.getValue())||value == 0){
                return true;
            }
        }
        return false;
    }
}
