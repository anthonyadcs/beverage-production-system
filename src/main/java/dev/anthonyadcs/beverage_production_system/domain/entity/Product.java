package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.ProductUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
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

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(length = 50, nullable = false, unique = true, updatable = false))
    private EntityCode code;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductUnitOfMeasure unitOfMeasure;

    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal volumePerUnit;

    @Column(nullable = false)
    private boolean active;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    @OneToMany(mappedBy = "product")
    List<Recipe> recipes = new ArrayList<>();

    public Product(EntityCode code, String name, String description, ProductUnitOfMeasure unitOfMeasure, BigDecimal volumePerUnit){
        validate(code, name, unitOfMeasure, volumePerUnit);

        this.code = code;
        this.name = name;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.volumePerUnit = volumePerUnit;
    }

    public String getCode() {
        return code.getCode();
    }

    public void activate(){
        if(this.active){
            throw new InvalidEntityStateException("O produto já está ativo.");
        }

        this.active = true;
    }

    public void deactivate(){
        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO PRODUTO

        if(!this.active){
            throw new InvalidEntityStateException("O produto já está inativo.");
        }

        this.active = false;
    }

    public void update(String name, String description, ProductUnitOfMeasure unitOfMeasure, BigDecimal volumePerUnit){
        if(!this.isActive()){
            throw new InvalidEntityStateException("O produto está inativo e não pode ser atualizado.");
        }

        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO PRODUTO

        if(name == null && description == null && unitOfMeasure == null && volumePerUnit == null){
            throw new InvalidArgumentException("Ao menos um campo deve ser fornecido para atualização.");
        }

        this.name = Optional.ofNullable(name).filter(n -> !n.isBlank()).orElse(this.name);
        this.description = Optional.ofNullable(description).filter(d -> !d.isBlank()).orElse(this.description);
        this.volumePerUnit = Optional.ofNullable(volumePerUnit).orElse(this.volumePerUnit);
    }

    private void validate(EntityCode code, String name, ProductUnitOfMeasure unitOfMeasure, BigDecimal volumePerUnit){
        Optional.ofNullable(code).orElseThrow(() -> new InvalidArgumentException("O código é necessário para criar um produto."));
        Optional.ofNullable(name).orElseThrow(() -> new InvalidArgumentException("O nome é necessário para criar um produto."));
        Optional.ofNullable(unitOfMeasure).orElseThrow(() -> new InvalidArgumentException("O código é necessário para criar um produto."));
        Optional.ofNullable(volumePerUnit).orElseThrow(() -> new InvalidArgumentException("O código é necessário para criar um produto."));
    }

    @PrePersist
    protected void prePersist(){
        this.createdAt = Instant.now();
        this.active = true;
    }

}
