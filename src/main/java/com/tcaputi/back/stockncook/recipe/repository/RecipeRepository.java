package com.tcaputi.back.stockncook.recipe.repository;

import com.tcaputi.back.stockncook.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
