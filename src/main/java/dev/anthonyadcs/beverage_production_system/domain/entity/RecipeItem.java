package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
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

    @Column(nullable = false, updatable = false, precision = 10, scale = 3)
    private BigDecimal quantity;

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
        Optional.ofNullable(recipe).orElseThrow(
                () -> new InvalidArgumentException("É necessário informar uma receita válida ao adicionar itens.")
        );

        Optional.ofNullable(rawMaterial).orElseThrow(
                () -> new InvalidArgumentException("É necessário informar um insumo válido ao adicionar itens.")
        );

        Optional.ofNullable(quantity).orElseThrow(
                () -> new InvalidArgumentException("Não é possível adicionar um item à receita sem informar a quantidade do insumo.")
        );
    }

    @PrePersist
    protected void prePersist(){
        this.createdAt = Instant.now();
    }
}
