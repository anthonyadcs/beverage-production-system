package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, UUID>, JpaSpecificationExecutor<RawMaterial> {
    boolean existsByCode(EntityCode code);
}
