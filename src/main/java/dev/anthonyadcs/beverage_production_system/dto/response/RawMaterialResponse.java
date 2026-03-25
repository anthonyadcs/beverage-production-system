package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.Material;
import dev.anthonyadcs.beverage_production_system.domain.enums.MaterialUnitOfMeasure;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MaterialResponse(
        UUID id,
        String name,
        String code,
        MaterialUnitOfMeasure unitOfMeasure,
        BigDecimal actualStock,
        BigDecimal minimalStock,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
    public static MaterialResponse fromEntity(Material material){
        return new MaterialResponse(
                material.getId(),
                material.getName(),
                material.getCode(),
                material.getUnitOfMeasure(),
                material.getActualStock(),
                material.getMinimalStock(),
                material.isActive(),
                material.getCreatedAt(),
                material.getUpdatedAt()
        );
    }

}
