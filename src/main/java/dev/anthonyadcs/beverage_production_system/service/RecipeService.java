package dev.anthonyadcs.beverage_production_system.service;

import dev.anthonyadcs.beverage_production_system.domain.entity.Product;
import dev.anthonyadcs.beverage_production_system.domain.entity.RawMaterial;
import dev.anthonyadcs.beverage_production_system.domain.entity.Recipe;
import dev.anthonyadcs.beverage_production_system.domain.entity.RecipeItem;
import dev.anthonyadcs.beverage_production_system.dto.request.CreateRecipeRequest;
import dev.anthonyadcs.beverage_production_system.dto.request.RecipeItemRequest;
import dev.anthonyadcs.beverage_production_system.dto.response.RecipeResponse;
import dev.anthonyadcs.beverage_production_system.exception.InvalidArgumentException;
import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityStateException;
import dev.anthonyadcs.beverage_production_system.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class RecipeService {
    @Autowired
    private ProductService productService;

    @Autowired
    private RawMaterialService rawMaterialService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public RecipeResponse create(UUID productId, CreateRecipeRequest recipeRequest){
        Product product = productService.findProductByIdOrThrow(productId);

        if(!product.isActive()){
            throw new InvalidEntityStateException("O produto está inativo e não pode ter novas receitas vinculadas a ele.");
        }

        Integer newVersion = recipeRepository.findMaxVersionByProduct(product) + 1;
        Recipe recipe = new Recipe(recipeRequest.description(), product, newVersion);

        Set<UUID> usedRawMaterialId = new HashSet<>();
        for(RecipeItemRequest recipeItemRequest : recipeRequest.items()){
            RawMaterial rawMaterial = rawMaterialService.findRawRawMaterialById(recipeItemRequest.rawMaterialId());

            if(!rawMaterial.isActive()){
                throw new InvalidEntityStateException("O insumo está inativo e não pode ser utilizado em receitas.");
            }

            if(!usedRawMaterialId.add(rawMaterial.getId())){
                throw new InvalidArgumentException("Não é permitido repetir insumos na receita.");
            }

            RecipeItem recipeItem = new RecipeItem(
                    recipe,
                    rawMaterial,
                    recipeItemRequest.quantity()
            );

            recipe.addRecipeItem(recipeItem);
        }

        recipeRepository.findTopByProductAndActiveTrueOrderByVersionDesc(product).ifPresent(Recipe::deactivate);
        recipe.activate();
        recipeRepository.save(recipe);

        return RecipeResponse.fromEntity(recipe);
    }
}
