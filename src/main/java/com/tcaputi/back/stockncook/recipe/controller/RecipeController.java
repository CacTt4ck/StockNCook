package com.tcaputi.back.stockncook.recipe.controller;

import com.tcaputi.back.stockncook.recipe.model.Recipe;
import com.tcaputi.back.stockncook.recipe.model.RecipeDto;
import com.tcaputi.back.stockncook.recipe.model.RecipeIngredient;
import com.tcaputi.back.stockncook.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public Recipe createRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeService.createRecipe(recipeDto);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/{id}/simulate")
    public String simulateRecipePreparation(@PathVariable Long id) {
        recipeService.simulateRecipePreparation(id);
        return "Enough stock available to prepare the recipe!";
    }

    @GetMapping("/{id}/missing")
    public List<String> getMissingIngredients(@PathVariable Long id) {
        return recipeService.getMissingIngredients(id);
    }

    @PostMapping("/{id}/prepare")
    public String prepareRecipe(@PathVariable Long id) {
        recipeService.prepareRecipe(id);
        return "Recipe prepared successfully!";
    }

}
