package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateProductRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.UpdateProductRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.PageResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.ProductResponse;
import dev.anthonyadcs.beverage_production_system.exception.ProductNotFoundException;
import dev.anthonyadcs.beverage_production_system.repository.ProductRepository;
import dev.anthonyadcs.beverage_production_system.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse create(CreateProductRequest productRequest){
        EntityCode code;
        do{
            code = EntityCode.create("PROD");
        } while(productRepository.existsByCode(code));

        Product product = new Product(code, productRequest);

        return ProductResponse.fromEntity(
                productRepository.save(product)
        );
    }

    @Transactional
    public ProductResponse updateStatus(UUID id, String action) {
        Product product = findProductByIdOrThrow(id);

        switch (action) {
            case "activate" -> product.activate();
            case "deactivate" -> product.deactivate();
            default -> {
                throw new IllegalArgumentException();
            }
        }

        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public ProductResponse update(UUID id, UpdateProductRequest productRequest){
        Product product = findProductByIdOrThrow(id);

        product.update(productRequest);

        return ProductResponse.fromEntity(product);
    }

    public ProductResponse getById(UUID id){
        return ProductResponse.fromEntity(findProductByIdOrThrow(id));
    }

    public PageResponse<ProductResponse> getAll(List<Boolean> activeValues, String name, String code, Pageable pageable){
        Specification<Product> specification = Specification.where(ProductSpecification.isActiveIn(activeValues) );

        if(name != null && !name.isBlank()){
            specification = specification.and(ProductSpecification.hasNameIgnoreCase(name));
        }

        if(code != null && !code.isBlank()){
            specification = specification.and(ProductSpecification.hasCode(code));
        }

        //Busca por todos os critérios e, com o map, transforma Product em ProductResponse
        Page<ProductResponse> productResponsePage = productRepository.findAll(specification, pageable).map(ProductResponse::fromEntity);

        //Transforma Page em PageResponse para melhor filtro dos campos retornados na requisição
        return PageResponse.fromPage(productResponsePage);
    }

    //Utilitário utilizado para evitar repetição de código.
    protected Product findProductByIdOrThrow(UUID id){
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Produto", "id", String.valueOf(id))
        );
    }
}
