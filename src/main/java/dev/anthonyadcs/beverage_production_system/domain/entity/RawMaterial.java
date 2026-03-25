package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(length = 50, nullable = false, unique = true, updatable = false))
    private EntityCode code;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RawMaterialUnitOfMeasure unitOfMeasure;

    @PositiveOrZero
    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal actualStock = BigDecimal.valueOf(0.000);

    @PositiveOrZero
    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal minimalStock;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    protected RawMaterial() {}

    public RawMaterial(EntityCode code, CreateRawMaterialRequest rawMaterialFields) {
        this.code = code;
        this.name = rawMaterialFields.name();
        this.description = rawMaterialFields.description();
        this.unitOfMeasure = rawMaterialFields.unitOfMeasure();
        this.minimalStock = rawMaterialFields.minimalStock();
    }

    public String getCode(){
        return code.getCode();
    }
}
