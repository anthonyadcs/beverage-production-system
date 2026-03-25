package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateStockMovementRequest (
        @NotNull(message = "O tipo de movimentação de estoque é necessário para sua criação.")
        StockMovementType type,
        BigDecimal movedQuantity,
        String reason;
) {}
