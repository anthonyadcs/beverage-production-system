package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.UpdateRawMaterialRequest;
import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityStateException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Getter
@Table(name = "raw_materials")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal actualStock;

    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal minimumStock;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    @OneToMany(mappedBy = "rawMaterial", fetch = FetchType.LAZY)
    private Set<StockMovement> stockMovements = new HashSet<>();

    public RawMaterial(EntityCode code, String name, String description, BigDecimal minimumStock, RawMaterialUnitOfMeasure unitOfMeasure) {
        validate(name, minimumStock, unitOfMeasure);
        Optional.ofNullable(code).orElseThrow(() -> new InvalidArgumentException("O código interno do insumo é necessário para sua criação."));

        this.code = code;
        this.name = name;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.minimumStock = minimumStock;
    }

    public String getCode() {
        return code.getCode();
    }

    public void activate() {
        if (this.active) {
            throw new InvalidEntityStateException("O insumo já está ativo.");
        }

        this.active = true;
    }

    public void deactivate() {
        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO INSUMO
        //TODO: IMPLEMENTAR REGRA QUANDO TIVER RECEITAS ATIVAS VINCULADAS AO INSUMO

        if (!this.active) {
            throw new InvalidEntityStateException("O insumo já está inativo.");
        }

        this.active = false;
    }

    public void update(String name, String description, BigDecimal minimumStock, RawMaterialUnitOfMeasure unitOfMeasure) {
        if (!this.isActive()) {
            throw new InvalidEntityStateException("O produto está inativo e não pode ser atualizado.");
        }
        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO PRODUTO

        if(Stream.of(name, description, minimumStock, unitOfMeasure).allMatch(Objects::isNull)){
            throw new InvalidArgumentException("É necessário fornecer pelo menos um campo para atualização.");
        }

        this.name = Optional.ofNullable(name).filter(n -> !n.isBlank()).orElse(this.name);
        this.description = Optional.ofNullable(description).filter(n -> !n.isBlank()).orElse(this.description);
        this.minimumStock = Optional.ofNullable(minimumStock).orElse(this.minimumStock);
        this.unitOfMeasure = Optional.ofNullable(unitOfMeasure).orElse(this.unitOfMeasure);
    }

    public BigDecimal calculateStockAfterMovement(StockMovementType movementType, BigDecimal movedQuantity, BigDecimal previousStock) {
        return switch (movementType) {
            case INBOUND -> previousStock.add(movedQuantity);
            case OUTBOUND -> {
                BigDecimal calc = previousStock.subtract(movedQuantity);

                if (calc.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvalidArgumentException("A quantidade de insumo movimentada é superior a quantidade presente em estoque.");
                }

                yield calc;
            }
            case ADJUSTMENT -> movedQuantity;
        };
    }

    public void applyStockMovement(BigDecimal resultingStock) {
        this.actualStock = resultingStock;
    }

    private void validate(String name, BigDecimal minimumStock, RawMaterialUnitOfMeasure unitOfMeasure) {
        Optional.ofNullable(name).orElseThrow(() -> new InvalidArgumentException("O código do insumo é necessário para sua criação."));
        Optional.ofNullable(minimumStock).orElseThrow(
                () -> new InvalidArgumentException("O estoque mínimo do insumo é necessário para sua criação.")
        );
        Optional.ofNullable(unitOfMeasure).orElseThrow(() -> new InvalidArgumentException("A unidade de medida é necessária para sua criação."));
    }

    @PrePersist
    private void prePersist() {
        this.active = true;
        this.createdAt = Instant.now();
        this.actualStock = BigDecimal.valueOf(0.000);
    }
}
