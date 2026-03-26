package dev.anthonyadcs.beverage_production_system.dto.request;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public record GetRecipesByProductRequest(
        UUID productId,
        List<Boolean> activeValues,
        Pageable pageable
) {}
