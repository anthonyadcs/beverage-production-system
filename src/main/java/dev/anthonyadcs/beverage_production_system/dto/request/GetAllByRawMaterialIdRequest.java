package dev.anthonyadcs.beverage_production_system.dto.request;

import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetAllByRawMaterialIdRequest(
        @NotNull(message = "O código de identificação do insumo é necessário para buscar suas movimentações.")
        UUID id,

        @NotNull(message = "Os métodos de paginação são necessário para buscar movimentações de estoque.")
        Pageable pageable,

        List<StockMovementType> stockMovementTypes,
        Instant startRangeDate,
        Instant endRangeDate
) {}
