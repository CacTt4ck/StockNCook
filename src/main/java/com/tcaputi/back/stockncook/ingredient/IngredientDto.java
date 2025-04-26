package com.tcaputi.back.stockncook.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private Long id;

    private String name;

    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double calories;

    private Double quantity;
    private String ean13;

    private Ingredient.Unit unit;
}