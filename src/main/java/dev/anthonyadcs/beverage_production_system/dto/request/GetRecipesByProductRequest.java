package dev.anthonyadcs.beverage_production_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public record GetAllRecipesRequest(
        UUID productId,
        List<Boolean> activeValues,
        Pageable pageable
) {}
