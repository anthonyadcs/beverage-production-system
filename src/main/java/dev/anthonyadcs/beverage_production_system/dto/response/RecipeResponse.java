package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.Recipe;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RecipeResponse(
        @NotNull
        UUID id,

        @NotNull
        UUID productId,

        @NotNull
        Integer version,

        String description,

        @NotNull
        Boolean active,

        @NotNull
        List<RecipeItemResponse> items,

        @NotNull
        Instant createdAt
) {
        public static RecipeResponse fromEntity(Recipe recipe){
                return new RecipeResponse(
                        recipe.getId(),
                        recipe.getProduct().getId(),
                        recipe.getVersion(),
                        recipe.getDescription(),
                        recipe.isActive(),
                        recipe.getRecipeItems().stream().map(RecipeItemResponse::fromEntity).toList(),
                        recipe.getCreatedAt()
                );
        }
}
