package com.tcaputi.back.stockncook.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private Long id;

    @NotBlank
    private String name;

    @PositiveOrZero
    private Double proteins;

    @PositiveOrZero
    private Double fats;

    @PositiveOrZero
    private Double carbohydrates;

    @PositiveOrZero
    private Double calories;

    @Positive
    private Double quantity;

    private Ingredient.Unit unit;

    @Size(min = 13, max = 13)
    private String ean13;
}