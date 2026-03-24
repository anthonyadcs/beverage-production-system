package dev.anthonyadcs.beverage_production_system.specification;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {

    //Query para encontrar valores exatos os valor do parâmetro 'active'
    public static Specification<Product> isActiveIn(List<Boolean> activeValues) {
        return ((root, query, criteriaBuilder) -> root.get("active").in(activeValues));
    }

    //Query para encontrar valores exatos os valor do parâmetro 'active'
    public static Specification<Product> hasNameIgnoreCase(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    //Valor já presente na coluna "name" na tabela "Product"
                    criteriaBuilder.lower(root.get("name")),

                    //Valor comparado ao valor acima
                    "%" + name.toLowerCase() + "%"
            );
        });
    }

    public static Specification<Product> hasCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null || code.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("code"),
                    code
            );
        };
    }
}
