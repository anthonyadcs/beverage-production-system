package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SummarizedRawMaterialResponse(
        UUID id,
        String name,
        String code,
        RawMaterialUnitOfMeasure unitOfMeasure
) {
        public static SummarizedRawMaterialResponse fromEntity(RawMaterial rawMaterial){
                return new SummarizedRawMaterialResponse(
                        rawMaterial.getId(),
                        rawMaterial.getName(),
                        rawMaterial.getCode(),
                        rawMaterial.getUnitOfMeasure()
                );
        }
}
