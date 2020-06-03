package top.kindless.billtest.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.security.context.AuthContextHolder;

@Aspect
@Component
@Slf4j
public class UserAuthInterceptor {


    @Around("@annotation(top.kindless.billtest.security.annotation.UserAuth)")
    public Object authUser(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        log.info(methodName+"开始执行");
        try {
            Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
            String message = "未授权请先登录";
            if (authentication == null){
                throw new UnAuthorizedException(message);
            }
            if (authentication.getUser() == null){
                throw new UnAuthorizedException(message);
            }
            Object[] args = joinPoint.getArgs();
            return joinPoint.proceed(args);
        }finally {
            log.info(methodName+"执行完毕");
        }
    }
}
