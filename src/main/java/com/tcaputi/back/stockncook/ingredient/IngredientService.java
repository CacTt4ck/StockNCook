package com.tcaputi.back.stockncook.ingredient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Iterable<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IngredientException("Ingredient not found for id: [" + id + "]"));
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    public Ingredient updateIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
