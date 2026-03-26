package dev.anthonyadcs.beverage_production_system.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeItemRequest(
        @NotNull(message = "O código de identificação do insumo é necessário para adicioná-lo como um item de uma receita.")
        UUID rawMaterialId,

        @NotNull(message = "A quantidade do insumo utilizado para a produção da receita deve ser maior que 0.")
        @Digits(integer = 10, fraction = 3, message = "A quantidade de insumo utilizado deve ter no máximo 4 casas decimais.")
        @DecimalMin(value = "0.000", inclusive = false, message = "A quantidade do insumo utilizado para a produção da receita deve ser maior que 0.")
        BigDecimal quantity
) {}
