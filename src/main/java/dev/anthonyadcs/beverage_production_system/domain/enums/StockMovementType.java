package dev.anthonyadcs.beverage_production_system.domain.enums;

import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;

import java.util.Arrays;

public enum StockMovementType {
    INBOUND,
    OUTBOUND,
    ADJUSTMENT;

    public static boolean isValid(String stockMovementType){
        return Arrays.stream(values()).anyMatch(e -> e.name().equalsIgnoreCase(stockMovementType));
    }

    public static StockMovementType fromString(String stockMovementType) {
        return Arrays.stream(values()).filter(x -> x.name().equalsIgnoreCase(stockMovementType)).findFirst().orElseThrow(
                () -> new InvalidArgumentException(
                        "Tipo de movimentação de estoque inválida. Os valores permitidos são: 'ENTRADA', 'SAÍDA', e 'AJUSTE'."
                )
        );
    }
}
