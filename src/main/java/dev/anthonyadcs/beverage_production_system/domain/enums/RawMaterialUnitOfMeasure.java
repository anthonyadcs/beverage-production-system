package dev.anthonyadcs.beverage_production_system.domain.enums;

import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RawMaterialUnitOfMeasure {
    LITER,
    MILLILITER,
    KILOGRAM,
    GRAM,
    UNIT;

    public static boolean isValid(String unitOfMeasure){
        return Arrays.stream(values()).anyMatch(e -> e.name().equalsIgnoreCase(unitOfMeasure));
    }

    public static RawMaterialUnitOfMeasure fromString(String unitOfMeasure) {
        return Arrays.stream(values()).filter(x -> x.name().equalsIgnoreCase(unitOfMeasure)).findFirst().orElseThrow(
                () -> new InvalidArgumentException(
                        "Unidade de medida inválida. Os valores permitidos são: 'LITRO', 'MILILITRO', 'QUILOGRAMA', 'GRAMA' e 'UNIDADE'."
                )
        );
    }

    public static List<RawMaterialUnitOfMeasure> parseFromStringList(List<String> unitOfMeasures) {
        if(unitOfMeasures == null || unitOfMeasures.isEmpty()) return null;

        List<RawMaterialUnitOfMeasure> rawMaterialUnitOfMeasures = new ArrayList<>();

        for(String unitOf : unitOfMeasures){
            if(RawMaterialUnitOfMeasure.isValid(unitOf)){
                rawMaterialUnitOfMeasures.add(RawMaterialUnitOfMeasure.fromString(unitOf));
            }
        }

        return rawMaterialUnitOfMeasures;
    }
}
