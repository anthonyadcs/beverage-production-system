package dev.anthonyadcs.beverage_production_system.repository;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    //Encontra a última versão da receita por produto. Caso seja a primeira, retorna 0
    @Query("SELECT COALESCE(MAX(r.version), 0) FROM Recipe r WHERE r.product = :product")
    Integer findMaxVersionByProduct(Product product);
}
