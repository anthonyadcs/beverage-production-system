package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateProductRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.ProductResponse;
import dev.anthonyadcs.beverage_production_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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
}
