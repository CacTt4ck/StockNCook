package com.tcaputi.back.stockncook.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private String name;
    private String description;
    private String instructions;
    private List<RecipeIngredientDto> ingredients;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeIngredientDto {
        private Long ingredientId;
        private Double amountRequired;
    }
}
