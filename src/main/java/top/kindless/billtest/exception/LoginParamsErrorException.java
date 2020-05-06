package top.kindless.billtest.exception;

public class LoginParamsErrorException extends BadRequestException {
    public LoginParamsErrorException(String message) {
        super(message);
    }

    public LoginParamsErrorException(String message, Throwable t) {
        super(message, t);
    }
}
