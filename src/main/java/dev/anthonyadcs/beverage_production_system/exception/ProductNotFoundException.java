package dev.anthonyadcs.beverage_production_system.exception;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(String resource, String identifierField, String identifierValue) {
        super(resource + "não encontrado com " + identifierField + " '" + identifierValue + "'.");
    }
}
