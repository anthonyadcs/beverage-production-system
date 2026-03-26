package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "recipe_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"raw_material_id", "recipe_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", updatable = false, nullable = false)
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "recipe_id", updatable = false, nullable = false)
    private Recipe recipe;

    @Positive
    @Column(nullable = false, updatable = false)
    private BigDecimal quantity;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
    private Instant createdAt;

    public RecipeItem(Recipe recipe, RawMaterial rawMaterial, BigDecimal quantity){
        validate(
                recipe,
                rawMaterial,
                quantity
        );

        this.rawMaterial = rawMaterial;
        this.recipe = recipe;
        this.quantity = quantity;
    }

    private void validate(Recipe recipe, RawMaterial rawMaterial,  BigDecimal quantity) {
        if (recipe == null) {
            throw new InvalidArgumentException("O código de identificação da receita é necessário para adicionar itens à mesma.");
        }

        if (rawMaterial == null) {
            throw new InvalidArgumentException("O código de identificação do insumo é necessário para adicioná-lo como um item de uma receita.");
        }

        if (quantity == null) {
            throw new InvalidArgumentException("A quantidade do insumo é necessário para a criação da receita.");
        }

        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidArgumentException("A quantidade do insumo utilizado para a produção da receita deve ser maior que 0.");
        }
    }

    @PrePersist
    protected void prePersist(){
        this.createdAt = Instant.now();
    }
}
