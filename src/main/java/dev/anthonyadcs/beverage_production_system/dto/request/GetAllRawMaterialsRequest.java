package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record GetAllRawMaterialsRequest(
        String name,

        @Size(min = 1)
        List<Boolean> activeValues,

        @Size(min = 1)
        List<RawMaterialUnitOfMeasure> unitOfMeasures,

        boolean lowStock,

        @NotNull
        Pageable pageable
) {}
