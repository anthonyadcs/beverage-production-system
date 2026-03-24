package dev.anthonyadcs.beverage_production_system.exception;

public class InvalidEntityCodeException extends RuntimeException {
    public InvalidEntityCodeException(String message) {
        super(message);
    }
}
