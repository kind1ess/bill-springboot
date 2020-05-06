package top.kindless.billtest.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends AbstractBaseException {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
