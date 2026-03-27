package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.Machine;
import dev.anthonyadcs.beverage_production_system.domain.enums.CapacityUnit;
import dev.anthonyadcs.beverage_production_system.domain.enums.MachineStatus;
import dev.anthonyadcs.beverage_production_system.domain.enums.MachineType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MachineResponse(
        UUID id,
        String name,
        String code,
        String description,
        MachineType type,
        MachineStatus status,
        BigDecimal productionCapacity,
        CapacityUnit capacityUnit,
        Instant createdAt,
        Instant updatedAt
) {
    public static MachineResponse fromEntity(Machine machine){
        return new MachineResponse(
                machine.getId(),
                machine.getName(),
                machine.getCode(),
                machine.getDescription(),
                machine.getType(),
                machine.getStatus(),
                machine.getProductionCapacity(),
                machine.getCapacityUnit(),
                machine.getCreatedAt(),
                machine.getUpdatedAt()
        );
    }
}
