package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.ProductUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(length = 50, nullable = false, unique = true, updatable = false)
    )
    private EntityCode code;

    @Setter
    @Column(length = 500)
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private ProductUnitOfMeasure unitOfMeasure;

    @PositiveOrZero
    @Setter
    @Column(precision = 10, scale = 3)
    private BigDecimal volumePerUnit;

    @Setter
    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    protected Product(){}

    public Product(EntityCode code, CreateProductRequest productFields){
        this.code = code;
        this.name = productFields.name();
        this.description = productFields.description();
        this.unitOfMeasure = productFields.unitOfMeasure();
        this.volumePerUnit = productFields.volumePerUnit();
    }

    public String getCode() {
        return code.getCode();
    }
}
