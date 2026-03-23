package dev.anthonyadcs.beverage_production_system.dto;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.enums.ProductUnitOfMeasure;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String code,
        String description,
        ProductUnitOfMeasure unitOfMeasure,
        BigDecimal volumePerUnit,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
    public static ProductResponse fromEntity(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getDescription(),
                product.getUnitOfMeasure(),
                product.getVolumePerUnit(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
