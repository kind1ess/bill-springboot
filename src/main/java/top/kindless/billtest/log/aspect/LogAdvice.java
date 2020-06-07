package top.kindless.billtest.log.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kindless.billtest.log.annotation.SysLog;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.entity.SysLogEntity;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.SysLogService;
import top.kindless.billtest.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Order(value = 2)
public class LogAdvice {

    private final SysLogService sysLogService;

    public LogAdvice(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(top.kindless.billtest.log.annotation.SysLog)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(args);
        long time = System.currentTimeMillis() - beginTime;
        doSave(joinPoint,time);
        return result;
    }

    private void doSave(ProceedingJoinPoint joinPoint,Long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogEntity sysLogEntity = new SysLogEntity();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null){
            sysLogEntity.setOperation(sysLog.value());
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setRequestMethod(className+"."+methodName+"()");
        Object[] args = joinPoint.getArgs();
        log.warn("参数们"+Arrays.toString(args));
        String requestParams = JsonUtils.convertToJson(args);
        log.warn("参数。。。。"+requestParams);
        sysLogEntity.setRequestParams(requestParams);
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"验证信息不能为空");
        Staff staff = authentication.getStaff();
        sysLogEntity.setStaffAccount(staff.getAccount());
        sysLogEntity.setStaffName(staff.getName());
        sysLogEntity.setTime(time);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes,"请求属性不能为空");
        HttpServletRequest request = requestAttributes.getRequest();
        log.warn("remoteAddr"+request.getRemoteAddr());
        String ip = request.getRemoteAddr();
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            //根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            assert inet != null;
            ip = inet.getHostAddress();
        }
        sysLogEntity.setIp(ip);
        log.info("ip....."+ip);
        sysLogEntity.setIp(ip);
        sysLogService.save(sysLogEntity);
    }
}
