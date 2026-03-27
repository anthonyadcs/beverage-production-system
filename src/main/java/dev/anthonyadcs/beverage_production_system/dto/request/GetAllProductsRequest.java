package dev.anthonyadcs.beverage_production_system.dto.request;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record GetAllProductsRequest(
        List<Boolean> activeValues,
        String name,
        String code,
        Pageable pageable
) {}
