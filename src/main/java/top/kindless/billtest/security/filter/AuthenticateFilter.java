package top.kindless.billtest.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;
import top.kindless.billtest.exception.AbstractBaseException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.entity.User;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.auth.AuthenticationImpl;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.context.AuthContextImpl;
import top.kindless.billtest.security.handler.AuthenticateFailHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Slf4j
public class    AuthenticateFilter extends OncePerRequestFilter {

    protected final AntPathMatcher antPathMatcher;
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private AuthenticateFailHandler authenticateFailHandler;
    private final RedisTemplate<String,Object> redisTemplate;

    private Set<String> excludeUrlPatterns = new HashSet<>(16);
    private Set<String> urlPatterns = new LinkedHashSet<>();

    AuthenticateFilter(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        antPathMatcher = new AntPathMatcher();
        authenticateFailHandler = new AuthenticateFailHandler();
        addUrlPatterns("/api/**");
        addExcludeUrlPatterns(
                "/api/user/signUp",
                "/api/user/login",
                "/api/goods/findAll"
        );
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            doAuthenticate(request,response,filterChain);
        } catch (AbstractBaseException e) {
            authenticateFailHandler.handleAuthenticateFail(request,response,e);
        }finally {
            AuthContextHolder.clearContext();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Assert.notNull(request, "请求不能为空");

        // 验证是否在过滤白名单
        boolean result = excludeUrlPatterns.stream().anyMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

        return result || urlPatterns.stream().noneMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));
    }

    private void addUrlPatterns(@NonNull String... urlPatterns){
        Assert.notNull(urlPatterns, "url模式不能为空");
        Collections.addAll(this.urlPatterns,urlPatterns);
    }

    private void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns){
        Assert.notNull(excludeUrlPatterns,"url白名单不能为空");
        Collections.addAll(this.excludeUrlPatterns,excludeUrlPatterns);
    }

    private void doAuthenticate(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
        User user = null;
        Staff staff = null;
        String token = request.getHeader("Authorization");
        if (token == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Object o = redisTemplate.opsForValue().get(token);
        if (o == null){
            throw new UnAuthorizedException("授权信息已经过期");
        }else {
            String className = o.getClass().getName();
            if (className.equals("top.kindless.billtest.model.entity.User")){
                user = (User) o;
            }else {
                staff = (Staff) o;
            }
            AuthContextHolder.setContext(new AuthContextImpl(new AuthenticationImpl(user,staff, token)));
            filterChain.doFilter(request,response);
        }
    }
}
