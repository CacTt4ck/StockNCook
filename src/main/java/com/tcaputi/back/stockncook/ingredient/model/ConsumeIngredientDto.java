package com.tcaputi.back.stockncook.ingredient.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ConsumeIngredientDto {

    @NotNull
    private Long ingredientId;

    @NotNull
    @Positive
    private Double amountInGrams;
}
