package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateStockMovementRequest(
        @NotNull(message = "O tipo de movimentação de estoque é necessário para sua criação.")
        StockMovementType type,

        @NotNull(message = "A quantidade do insumo utilizada na movimentação de estoque é necessária para sua criação.")
        @DecimalMin(value = "0.000", inclusive = false, message = "A quantidade do insumo utilizada deve ser maior que 0")
        @Digits(integer = 10, fraction = 3, message = "A quantidade de insumo utilizada na movimentação deve ter no máximo 3 casas decimais.")
        BigDecimal movedQuantity,

        @NotBlank(message = "O motivo da movimentação de estoque é necessário para sua criação.")
        @Size(min = 3, max = 500, message = "O motivo da motivação do estoque deve ter entre 3 e 500 caracteres.")
        String reason,

        UUID productionOrderId
) {
}
