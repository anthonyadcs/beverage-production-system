package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateRawMaterialRequest(
        @Size(min = 2, max = 100, message = "O nome do insumo deve ter entre 2 e 100 caracteres")
        String name,

        @Size(max = 500, message = "O números de caracteres máximo aceito para a descrição do insumo é 500.")
        String description,

        @DecimalMin(value = "0.000", inclusive = false)
        @Digits(integer = 10, fraction = 3, message = "O estoque mínimo deve ter no máximo 3 casas decimais.")
        BigDecimal minimumStock,

        RawMaterialUnitOfMeasure unitOfMeasure
) {
    public boolean isEmpty() {
        return name == null && description == null && minimumStock == null;
    }
}
