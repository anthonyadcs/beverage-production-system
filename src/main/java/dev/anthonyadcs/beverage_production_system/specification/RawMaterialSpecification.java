package dev.anthonyadcs.beverage_production_system.specification;

import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.enums.RawMaterialUnitOfMeasure;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RawMaterialSpecification {
    public static Specification<RawMaterial> isActiveIn(List<Boolean> activeValues) {
        return ((root, query, criteriaBuilder) -> root.get("active").in(activeValues));
    }

    //Query para encontrar valores exatos os valor do parâmetro 'active'
    public static Specification<RawMaterial> hasNameIgnoreCase(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    //Valor já presente na coluna "name" na tabela "RawMaterial"
                    criteriaBuilder.lower(root.get("name")),

                    //Valor comparado ao valor acima
                    "%" + name.toLowerCase() + "%"
            );
        });
    }

    public static Specification<RawMaterial> hasUnitOfMeasure(List<RawMaterialUnitOfMeasure> unitOfMeasures) {
        return (root, query, criteriaBuilder) -> root.get("unitOfMeasure").in(unitOfMeasures);
    }

    public static Specification<RawMaterial> hasLowStock() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(
                root.get("actualStock"),
                root.get("minimalStock")
        );
    }
}
