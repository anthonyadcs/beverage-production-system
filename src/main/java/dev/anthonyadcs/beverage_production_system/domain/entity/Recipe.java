package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Table(name = "recipes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @Positive
    @Column(nullable = false, updatable = false)
    private Integer version;

    @Column(columnDefinition = "TEXT", length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = false;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<RecipeItem> recipeItems = new ArrayList<>();

    public Recipe(String description, Product product, Integer version){
        validate(product);

        this.description = description;
        this.product = product;
        this.version = version;
    }

    public void addRecipeItem(RecipeItem recipeItem){
        this.recipeItems.add(recipeItem);
    }

    private void validate(Product product){
        if(product == null){
            throw new InvalidArgumentException("O código de identificação do produto é necessário para a criação de uma receita.");
        }
    }

    @PrePersist
    protected void prePersist(){
        this.createdAt = Instant.now();
    }
}
