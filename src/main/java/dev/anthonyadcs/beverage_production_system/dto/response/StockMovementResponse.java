package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.StockMovement;
import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record StockMovementResponse(
        UUID id,
        UUID rawMaterialId,
        StockMovementType type,
        BigDecimal movedQuantity,
        BigDecimal previousStock,
        BigDecimal resultingStock,
        String reason,
        //TODO: ADICIONAR "Production Order" ID NESTE RESPONSE QUANDO TIVER RELAÇÃO NA ENTIDADE
        Instant createdAt
) {
    public static StockMovementResponse fromEntity(StockMovement stockMovement){
        return new StockMovementResponse(
                stockMovement.getId(),
                stockMovement.getRawMaterial().getId(),
                stockMovement.getType(),
                stockMovement.getMovedQuantity(),
                stockMovement.getPreviousStock(),
                stockMovement.getResultingStock(),
                stockMovement.getReason(),
                stockMovement.getCreatedAt()
        );
    }

}
