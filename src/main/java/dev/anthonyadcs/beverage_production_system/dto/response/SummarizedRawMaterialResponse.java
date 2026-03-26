package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SummarizedRawMaterialResponse(
        @NotNull(message = "O código de identificação do insumo é necessário para que ele seja exibido corretamente.")
        UUID id,
        @NotBlank(message = "O nome do insumo é necessário para que ele seja exibido corretamente.")
        String name,
        @NotBlank(message = "O código interno do insumo é necessário para que ele seja exibido corretamente.")
        String code,
        @NotNull(message = "A unidade de medida do insumo é necessário para que ele seja exibido corretamente.")
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
