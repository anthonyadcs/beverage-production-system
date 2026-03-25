package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateRawMaterialRequest(
        @NotBlank(message = "O nome do produto é necessário para sua criação.")
        @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres")
        String name,

        @Size(max = 500, message = "O números de caracteres máximo aceito para a descrição do produto é 500.")
        String description,

        @NotNull(message = "A unidade de medida do produto é necessária para sua criação.")
        RawMaterialUnitOfMeasure unitOfMeasure,

        @NotNull(message = "O estoque mínimo do insumo é necessário para sua criação.")
        @DecimalMin(value = "0.000", inclusive = false, message = "O estoque mínimo do produto deve ser maior que 0.")
        BigDecimal minimumStock
) {
}
