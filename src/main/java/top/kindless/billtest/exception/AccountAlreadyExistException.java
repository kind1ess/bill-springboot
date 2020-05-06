package top.kindless.billtest.exception;

public class AccountAlreadyExistException extends BadRequestException{
    public AccountAlreadyExistException(String message) {
        super(message);
    }

    public AccountAlreadyExistException(String message, Throwable t) {
        super(message, t);
    }
}
