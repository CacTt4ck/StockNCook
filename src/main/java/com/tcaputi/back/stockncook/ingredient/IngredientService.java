package com.tcaputi.back.stockncook.ingredient;

import com.tcaputi.back.stockncook.ingredient.openfoodfacts.OpenFoodFactsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final OpenFoodFactsService openFoodFactsService;

    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IngredientException("Ingredient not found for id: [" + id + "]"));
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredient.getEan13() != null) {
            return ingredientRepository.findByEan13(ingredient.getEan13())
                    .orElseGet(() -> {
                        log.info("Fetching ingredient from OpenFoodFacts for EAN: [{}]", ingredient.getEan13());
                        Optional<Ingredient> fromOpenFoodFacts = openFoodFactsService.fetchIngredientFromOpenFoodFacts(ingredient.getEan13());
                        return fromOpenFoodFacts.map(ingredientRepository::save).orElseGet(() -> ingredientRepository.save(ingredient));
                    });
        } else {
            return ingredientRepository.save(ingredient);
        }
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    public Ingredient updateIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
