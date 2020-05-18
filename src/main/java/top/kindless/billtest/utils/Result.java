package top.kindless.billtest.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private T data;

    private String message;

    private Integer code;

    public static <T> Result<T> ok(String message){
        return new Result<>(null,message,HttpStatus.OK.value());
    }

    public static <T> Result<T> ok(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.OK.value());
    }

    public static <T> Result<T> notFound(String message){
        return new Result<>(null,message,HttpStatus.NOT_FOUND.value());
    }

    public static <T> Result<T> notFound(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.NOT_FOUND.value());
    }

    public static <T> Result<T> badRequest(String message){
        return new Result<>(null,message,HttpStatus.BAD_REQUEST.value());
    }

    public static <T> Result<T> badRequest(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.BAD_REQUEST.value());
    }

    public static <T> Result<T> internalError(String message){
        return new Result<>(null,message,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static <T> Result<T> internalError(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static <T> Result<T> unAuthorized(String message){
        return new Result<>(null,message,HttpStatus.UNAUTHORIZED.value());
    }

    public static <T> Result<T> unAuthorized(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.UNAUTHORIZED.value());
    }

    public static <T> Result<T> forbidden(String message){
        return new Result<>(null,message,HttpStatus.FORBIDDEN.value());
    }

    public static <T> Result<T> forbidden(String message,@Nullable T data){
        return new Result<>(data,message,HttpStatus.FORBIDDEN.value());
    }
}
