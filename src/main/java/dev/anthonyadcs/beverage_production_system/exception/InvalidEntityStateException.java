package dev.anthonyadcs.beverage_production_system.exception;

public class InvalidEntityStateException extends RuntimeException {
    public InvalidEntityStateException(String message) {
        super(message);
    }
}
