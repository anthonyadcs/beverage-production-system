package dev.anthonyadcs.beverage_production_system.dto.response;

import dev.anthonyadcs.beverage_production_system.domain.entity.RecipeItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RecipeItemResponse(
        @NotNull(message = "O código de identificação da associação do insumo é necessário para que a receita seja exibida corretamente.")
        UUID id,

        @NotNull(message = "O insumo associado à receita deve estar presente para que a receita seja exibida corretamente.")
        SummarizedRawMaterialResponse rawMaterial,

        @NotNull(message = "A quantidade do insumo é necessária para que a receita seja exibida corretamente.")
        @DecimalMin(
                value = "0.000",
                inclusive = false,
                message = "A quantidade do insumo deve ser maior que zero para que a receita seja exibida corretamente."
        )
        @Digits(integer = 10, fraction = 3, message = "A quantidade de insumo utilizada deve ter no máximo 3 casas decimais.")
        BigDecimal quantity,

        @NotNull(message = "A data de criação do item da receita deve estar presente para histórico correto.")
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
