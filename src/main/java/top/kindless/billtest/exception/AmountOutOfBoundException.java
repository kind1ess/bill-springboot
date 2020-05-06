package top.kindless.billtest.exception;

public class AmountOutOfBoundException extends BadRequestException {
    public AmountOutOfBoundException(String message) {
        super(message);
    }

    public AmountOutOfBoundException(String message, Throwable t) {
        super(message, t);
    }
}
