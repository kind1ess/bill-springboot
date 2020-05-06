package top.kindless.billtest.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractBaseException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
