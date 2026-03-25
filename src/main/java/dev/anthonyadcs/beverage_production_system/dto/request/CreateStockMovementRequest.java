package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateStockMovementRequest (
        @NotNull(message = "O tipo de movimentação de estoque é necessário para sua criação.")
        StockMovementType type,

        @NotNull(message = "A quantidade do insumo utilizada na movimentação de estoque é necessária para sua criação.")
        @DecimalMin(value = "0.000", inclusive = false, message = "A quantidade do insumo utilizada deve ser maior que 0")
        BigDecimal movedQuantity,

        @NotBlank(message = "O motivo da movimentação de estoque é necessário para sua criação.")
        String reason,

        UUID productionOrderId
) {}
