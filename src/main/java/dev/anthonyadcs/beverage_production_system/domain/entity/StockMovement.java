package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.StockMovementType;
import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Table(name = "stock_movements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    //RELAÇÃO DE "StockMovement" COM "RawMaterial" -> N:1
    @ManyToOne(optional = false)
    @JoinColumn(name = "raw_material_id", nullable = false, updatable = false)
    private RawMaterial rawMaterial;

    //TODO: IMPLEMENTAR RELAÇÃO DE FUTURA ENTIDADE "ProductionOrder" COM "StockMovement -> N:1"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private StockMovementType type;

    @Column(precision = 10, scale = 3, nullable = false, updatable = false)
    private BigDecimal movedQuantity;

    @Column(precision = 10, scale = 3, nullable = false, updatable = false)
    private BigDecimal previousStock;

    @Column(precision = 10, scale = 3, nullable = false, updatable = false)
    private BigDecimal resultingStock;

    @Column(length = 300, nullable = false, updatable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    public StockMovement(
            RawMaterial rawMaterial,
            StockMovementType type,
            BigDecimal movedQuantity,
            BigDecimal previousStock,
            BigDecimal resultingStock,
            String reason
            //TODO: ADICIONAR IMPLEMENTAÇÃO PARA "PRODUCTION_ORDER"
    ) {
        validate(
                rawMaterial,
                type,
                movedQuantity,
                previousStock,
                resultingStock,
                reason
        );

        this.rawMaterial = rawMaterial;
        this.type = type;
        this.movedQuantity = movedQuantity;
        this.previousStock = previousStock;
        this.resultingStock = resultingStock;
        this.reason = reason;
    }

    private void validate(
            RawMaterial rawMaterial,
            StockMovementType type,
            BigDecimal movedQuantity,
            BigDecimal previousStock,
            BigDecimal resultingStock,
            String reason
    ) {
        Optional.ofNullable(rawMaterial).orElseThrow(
                () -> new InvalidArgumentException("O insumo a ser movimentado é necessário para a criação de uma movimentação de estoque.")
        );

        Optional.ofNullable(type).orElseThrow(
                () -> new InvalidArgumentException("O tipo de movimentação de estoque é necessário para sua criação.")
        );

        Optional.ofNullable(reason).orElseThrow(
                () -> new InvalidArgumentException("O motivo da movimentação de estoque é necessário para sua criação.")
        );

        Optional.ofNullable(movedQuantity).orElseThrow(
                () -> new InvalidArgumentException("A quantidade do insumo movimentada deve ser maior que 0.")
        );

        Optional.ofNullable(previousStock).orElseThrow(
                () -> new InvalidArgumentException(
                        "O estoque anterior a movimentação do insumo é necessário para a criação de uma movimentação de estoque."
                )
        );

        Optional.ofNullable(resultingStock).orElseThrow(
                () -> new InvalidArgumentException("A quantidade de insumo movimentada é superior a quantidade presente em estoque.")
        );
    }

    @PrePersist
    private void prePersist(){
        this.createdAt = Instant.now();
    }
}
