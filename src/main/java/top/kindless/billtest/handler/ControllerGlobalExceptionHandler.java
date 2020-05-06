package top.kindless.billtest.handler;

import lombok.extern.slf4j.Slf4j;
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

//    @ExceptionHandler(AccountAlreadyExistException.class)
//    @ResponseBody
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    public Result handleAccountAlreadyExistException(AccountAlreadyExistException e){
//        log.error(e.getMessage());
//        return Result.badRequest(e.getMessage());
//    }
//
//    @ExceptionHandler(LoginParamsErrorException.class)
//    @ResponseBody
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    public Result handleLoginParamsErrorException(LoginParamsErrorException e){
//        log.error(e.getMessage());
//        return Result.badRequest(e.getMessage());
//    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleBadRequestException(BadRequestException e){
        log.error(e.getMessage());
        return Result.badRequest(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleInternalServerErrorException(InternalServerErrorException e){
        log.error(e.getMessage(),e);
        return Result.internalError("服务器发生错误",e.getErrorData());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Result handleUnAuthorizedException(UnAuthorizedException e){
        log.warn(e.getMessage());
        return Result.unAuthorized(e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Result handleForbiddenException(ForbiddenException e){
        log.warn(e.getMessage());
        return Result.forbidden(e.getMessage(),e.getErrorData());
    }
}
