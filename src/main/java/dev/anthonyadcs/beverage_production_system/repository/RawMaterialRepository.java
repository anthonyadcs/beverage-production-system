package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, UUID> {
    boolean existsByCode(EntityCode code);
}
