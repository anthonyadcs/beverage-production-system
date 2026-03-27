package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.CapacityUnit;
import dev.anthonyadcs.beverage_production_system.domain.enums.MachineStatus;
import dev.anthonyadcs.beverage_production_system.domain.enums.MachineType;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "machines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false, length = 100)
    private String name;

    @Column(length = 500, columnDefinition = "TEXT")
    private String description;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(length = 50, nullable = false, unique = true, updatable = false))
    private EntityCode code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private MachineType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private MachineStatus status;

    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal productionCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CapacityUnit capacityUnit;

    @Column(nullable = false, updatable = false, length = 100)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Machine(
            EntityCode code,  String name, String description, MachineType type, BigDecimal productionCapacity, CapacityUnit capacityUnit
    ){
        validate(name, type, productionCapacity, capacityUnit);

        this.name = name;
        this.name = name;
        this.name = name;
    }

    public Machine(String name, String description, EntityCode code, MachineType type, BigDecimal productionCapacity, CapacityUnit capacityUnit) {
        validate(name, type, productionCapacity, capacityUnit);
        Optional.ofNullable(code).orElseThrow(
                () -> new InvalidArgumentException("Informar o código interno da máquina é necessário para sua criação.")
        );

        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.productionCapacity = productionCapacity;
        this.capacityUnit = capacityUnit;
    }

    private void validate(String name, MachineType type, BigDecimal productionCapacity, CapacityUnit capacityUnit){
        Optional.ofNullable(name).orElseThrow(
                () -> new InvalidArgumentException("Informar o nome da máquina é necessário para sua criação.")
        );

        Optional.ofNullable(type).orElseThrow(
                () -> new InvalidArgumentException("Informar o tipo da máquina é necessário para sua criação.")
        );

        Optional.ofNullable(productionCapacity).orElseThrow(
                () -> new InvalidArgumentException("Informar a capacidade de produção da máquina é necessário para sua criação.\"")
        );

        Optional.ofNullable(capacityUnit).orElseThrow(
                () -> new InvalidArgumentException("Informar a unidade de medida da capacidade da máquina é necessário para sua criação.")
        );
    }

    public String getCode(){
        return code.getCode();
    }

    @PrePersist
    private void prePersist(){
        this.createdAt = Instant.now();
        this.status = MachineStatus.AVAILABLE;
    }
}
