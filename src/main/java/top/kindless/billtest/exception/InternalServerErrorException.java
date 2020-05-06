package top.kindless.billtest.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbstractBaseException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
