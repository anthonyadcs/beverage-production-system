package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RawMaterialResponse(
        UUID id,
        String name,
        String code,
        BigDecimal actualStock,
        BigDecimal minimalStock,
        RawMaterialUnitOfMeasure unitOfMeasure,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
    public static RawMaterialResponse fromEntity(RawMaterial rawMaterial){
        return new RawMaterialResponse(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getCode(),
                rawMaterial.getActualStock(),
                rawMaterial.getMinimalStock(),
                rawMaterial.getUnitOfMeasure(),
                rawMaterial.isActive(),
                rawMaterial.getCreatedAt(),
                rawMaterial.getUpdatedAt()
        );
    }

}
