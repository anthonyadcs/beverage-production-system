package dev.anthonyadcs.beverage_production_system.domain.entity;

import dev.anthonyadcs.beverage_production_system.domain.enums.ProductUnitOfMeasure;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateProductRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.UpdateProductRequest;
import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityStateException;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
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
    @AttributeOverride(name = "code", column = @Column(length = 50, nullable = false, unique = true, updatable = false))
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

    @Setter(AccessLevel.NONE)
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

    public void deactivate(){
        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO PRODUTO

        if(!this.active){
            throw new InvalidEntityStateException("O produto já está inativo.");
        }

        this.active = false;
    }

    public void activate(){
        if(this.active){
            throw new InvalidEntityStateException("O produto já está ativo.");
        }

        this.active = true;
    }

    public void update(UpdateProductRequest productRequest){
        if(productRequest.isEmpty()){
            throw new InvalidArgumentException(
                    "Ao menos um dos campos devem ser fornecidos para atualização: 'nome', 'descrição', 'unidade de medida', 'volume por unidade'"
            );
        }

        if(!this.isActive()){
            throw new InvalidEntityStateException("O produto está inativo e não pode ser atualizado.");
        }

        //TODO: IMPLEMENTAR REGRA QUANDO TIVER LISTA DE PRODUÇÕES VINCULADAS AO PRODUTO

        if(productRequest.name() != null && !productRequest.name().isBlank()){
            this.name = productRequest.name();
        }

        if(productRequest.description() != null && !productRequest.description().isBlank()){
            this.description = productRequest.description();
        }

        if(productRequest.unitOfMeasure() != null){
            this.unitOfMeasure = productRequest.unitOfMeasure();
        }

        if(productRequest.volumePerUnit() != null){
            this.volumePerUnit = productRequest.volumePerUnit();
        }
    }

}
