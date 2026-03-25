package dev.anthonyadcs.beverage_production_system.exception;

public class RawMaterialNotFoundException extends EntityNotFoundException {
    public RawMaterialNotFoundException(String resource, String identifierField, String identifierValue) {
        super(resource + " não encontrado com " + identifierField + " '" + identifierValue + "'.");
    }
}
