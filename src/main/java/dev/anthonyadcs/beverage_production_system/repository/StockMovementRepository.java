package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
}
