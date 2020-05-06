package top.kindless.billtest.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractBaseException{
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
