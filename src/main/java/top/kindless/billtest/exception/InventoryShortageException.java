package top.kindless.billtest.exception;

public class InventoryShortageException extends BadRequestException {
    public InventoryShortageException(String message) {
        super(message);
    }

    public InventoryShortageException(String message, Throwable t) {
        super(message, t);
    }
}
