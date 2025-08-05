package com.tcaputi.back.stockncook.ingredient.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Unit unit;

    @Size(min = 13, max = 13)
    private String ean13;
}