package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.valueObject.EntityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByCode(EntityCode code);
}
