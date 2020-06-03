package top.kindless.billtest.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.kindless.billtest.exception.*;
import top.kindless.billtest.utils.Result;


@ControllerAdvice
@Slf4j
public class ControllerGlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result<Object> handleBadRequestException(BadRequestException e){
        log.error(e.getMessage());
        return Result.badRequest(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> handleInternalServerErrorException(InternalServerErrorException e){
        log.error(e.getMessage(),e);
        return Result.internalError(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Result<Object> handleUnAuthorizedException(UnAuthorizedException e){
        log.warn(e.getMessage());
        return Result.unAuthorized(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Result<Object> handleForbiddenException(ForbiddenException e){
        log.warn(e.getMessage());
        return Result.forbidden(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result<Object> handleSqlException(DataAccessException e){
        log.warn(e.getMessage(),e.getCause());
        return Result.badRequest("无法执行数据操作，也许是因为数据不完整或者数据重复");
    }
}
