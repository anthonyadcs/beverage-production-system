package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.RecipeItem;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RecipeItemResponse(
        UUID id,
        SummarizedRawMaterialResponse rawMaterial,
        BigDecimal quantity,
        Instant createdAt
) {
    public static RecipeItemResponse fromEntity(RecipeItem recipeItem) {
        return new RecipeItemResponse(
                recipeItem.getId(),
                SummarizedRawMaterialResponse.fromEntity(recipeItem.getRawMaterial()),
                recipeItem.getQuantity(),
                recipeItem.getCreatedAt()
        );
    }
}
