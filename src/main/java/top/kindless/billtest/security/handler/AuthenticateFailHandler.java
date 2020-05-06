package top.kindless.billtest.security.handler;


import lombok.extern.slf4j.Slf4j;
import top.kindless.billtest.exception.AbstractBaseException;
import top.kindless.billtest.utils.JsonUtils;
import top.kindless.billtest.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticateFailHandler {

    public void handleAuthenticateFail(HttpServletRequest request, HttpServletResponse response, AbstractBaseException e) throws IOException, ServletException {
        log.warn(e.getMessage());
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(e.getHttpStatus().value());
        Result<Object> result = Result.unAuthorized(e.getMessage(),e.getErrorData());
        String jsonResult = JsonUtils.convertToJson(result);
        response.getWriter().write(jsonResult);
    }
}
