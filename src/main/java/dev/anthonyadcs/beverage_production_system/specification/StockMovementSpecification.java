package dev.anthonyadcs.beverage_production_system.specification;

import dev.anthonyadcs.beverage_production_system.domain.entity.StockMovement;
import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class StockMovementSpecification {
    public static Specification<StockMovement> hasRawMaterialId(UUID id) {
        return (root, query, criteriaBuilder) -> root.get("rawMaterial").get("id").equalTo(id);
    }

    public static Specification<StockMovement> hasType(List<StockMovementType> stockMovementTypes){
        return (root, query, criteriaBuilder) -> root.get("type").in(stockMovementTypes);
    }

    public static Specification<StockMovement> betweenDates(Instant startDateRange, Instant endDateRange){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get("createdAt"),
                startDateRange,
                endDateRange
        );
    }
}
