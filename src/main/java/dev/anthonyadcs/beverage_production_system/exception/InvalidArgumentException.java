package dev.anthonyadcs.beverage_production_system.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
