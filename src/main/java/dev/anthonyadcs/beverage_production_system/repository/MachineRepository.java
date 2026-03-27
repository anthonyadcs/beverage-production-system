package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.Machine;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MachineRepository extends JpaRepository<Machine, UUID> {
    boolean existsByCode(EntityCode code);
}
