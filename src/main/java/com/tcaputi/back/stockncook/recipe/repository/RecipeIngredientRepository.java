package com.tcaputi.back.stockncook.recipe.repository;

import com.tcaputi.back.stockncook.recipe.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
