package dev.anthonyadcs.beverage_production_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateRecipeRequest(
        @Size(min = 3, max = 500, message = "A descrição da receita deve ter entre 3 e 500 caracteres.")
        String description,

        @NotNull(message = "É necessário ao menos um insumo para a criação de uma receita.")
        @Size(min = 1, message = "É necessário ao menos um insumo para a criação de uma receita.")
        List<RecipeItemRequest> items
) {}