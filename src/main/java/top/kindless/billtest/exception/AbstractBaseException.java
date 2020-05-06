package top.kindless.billtest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class AbstractBaseException extends RuntimeException {

    private Object errorData;

    public AbstractBaseException(String message){
        super(message);
    }

    public AbstractBaseException(String message,Throwable t){
        super(message,t);
    }

    @Nullable
    public Object getErrorData(){
        return errorData;
    }

    @NonNull
    public abstract HttpStatus getHttpStatus();

    public AbstractBaseException setErrorData(@Nullable Object errorData){
        this.errorData = errorData;
        return this;
    }
}
