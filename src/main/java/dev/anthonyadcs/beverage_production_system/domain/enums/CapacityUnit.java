package dev.anthonyadcs.beverage_production_system.domain.enums;

import lombok.Getter;

@Getter
public enum CapacityUnit {
    LITER_PER_HOUR("L", "H"),
    MILLILITER_PER_HOUR("mL", "H"),
    KILOGRAM_PER_HOUR("Kg", "H"),
    UNIT_PER_HOUR("Unit", "H"),

    LITER_PER_SHIFT("L", "Shift"),
    MILLILITER_PER_SHIFT("mL", "Shift"),
    KILOGRAM_PER_SHIFT("Kg", "Shift"),
    UNIT_PER_SHIFT("Unit", "Shift");

    private final String quantityUnit;
    private final String timeUnit;

    CapacityUnit(String quantityUnit, String timeUnit){
        this.quantityUnit = quantityUnit;
        this.timeUnit = timeUnit;
    }
}
