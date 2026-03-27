package dev.anthonyadcs.beverage_production_system.exception;

public class RecipeNotFoundException extends EntityNotFoundException {
    public RecipeNotFoundException(String resource, String identifierField, String identifierValue) {
        super(resource + " não encontrado com " + identifierField + " '" + identifierValue + "'.");
    }
}
