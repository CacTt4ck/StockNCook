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
public class IngredientService {

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
                    .map(existing -> {
                        // 🛒 Ajout de la quantité achetée au stock existant
                        log.info("Adding quantity [{}] to existing ingredient [{}]", ingredient.getQuantity(), existing.getName());
                        existing.setQuantity(existing.getQuantity() + ingredient.getQuantity());
                        return ingredientRepository.save(existing);
                    })
                    .orElseGet(() -> {
                        // 🌍 Si pas trouvé localement, aller chercher sur OpenFoodFacts
                        log.info("Fetching ingredient from OpenFoodFacts for EAN: [{}]", ingredient.getEan13());
                        Optional<Ingredient> fromOpenFoodFacts = openFoodFactsService.fetchIngredientFromOpenFoodFacts(ingredient.getEan13());
                        return fromOpenFoodFacts.map(fetched -> {
                            fetched.setQuantity(ingredient.getQuantity()); // Mettre la quantité reçue
                            return ingredientRepository.save(fetched);
                        }).orElseGet(() -> ingredientRepository.save(ingredient));
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

    public Ingredient consumeIngredient(Long id, double amountInGrams) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientException("Ingredient not found for id: [" + id + "]"));

        double totalAvailable = (ingredient.getUnitStock() * ingredient.getUnitWeight())
                + (ingredient.getOpenQuantity() != null ? ingredient.getOpenQuantity() : 0.0);

        if (amountInGrams > totalAvailable) {
            throw new IngredientException("Not enough stock for ingredient id: [" + id + "]. Requested: "
                    + amountInGrams + "g, Available: " + totalAvailable + "g");
        }

        double remaining = amountInGrams;

        if (ingredient.getOpenQuantity() != null && ingredient.getOpenQuantity() > 0) {
            double usedFromOpen = Math.min(ingredient.getOpenQuantity(), remaining);
            ingredient.setOpenQuantity(ingredient.getOpenQuantity() - usedFromOpen);
            remaining -= usedFromOpen;
        }

        while (remaining > 0 && ingredient.getUnitStock() > 0) {
            ingredient.setUnitStock(ingredient.getUnitStock() - 1);
            double usedFromNewUnit = Math.min(ingredient.getUnitWeight(), remaining);
            remaining -= usedFromNewUnit;
            ingredient.setOpenQuantity(ingredient.getUnitWeight() - usedFromNewUnit);
        }

        // Recalculer la quantité totale
        double totalQuantity = (ingredient.getUnitStock() * ingredient.getUnitWeight())
                + (ingredient.getOpenQuantity() != null ? ingredient.getOpenQuantity() : 0.0);
        ingredient.setQuantity(totalQuantity);

        return ingredientRepository.save(ingredient);
    }

}
