package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.ProductUnitOfMeasure;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres")
        String name,

        @Size(max = 500, message = "O números de caracteres máximo aceito para a descrição do produto é 500.")
        String description,

        ProductUnitOfMeasure unitOfMeasure,

        @DecimalMin(
                value = "0.000",
                inclusive = false,
                message = "O volume por unidade do produto deve ser maior que 0."
        )
        BigDecimal volumePerUnit
) {
    public boolean isEmpty() {
        return name == null && description == null && volumePerUnit == null && unitOfMeasure == null;
    }
}
