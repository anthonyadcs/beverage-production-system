package dev.anthonyadcs.beverage_production_system.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateRawMaterialRequest(
        @Size(min = 2, max = 100, message = "O nome do insumo deve ter entre 2 e 100 caracteres")
        String name,

        @Size(max = 500, message = "O números de caracteres máximo aceito para a descrição do insumo é 500.")
        String description,

        @PositiveOrZero
        @DecimalMin(value = "0.000", inclusive = false)
        BigDecimal minimumStock
) {
    public boolean isEmpty() {
        return name == null && description == null && minimumStock == null;
    }
}
