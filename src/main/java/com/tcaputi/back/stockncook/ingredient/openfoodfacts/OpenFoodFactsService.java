package com.tcaputi.back.stockncook.ingredient.openfoodfacts;

import com.tcaputi.back.stockncook.ingredient.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Service
public class OpenFoodFactsService {

    private static final String OPENFOODFACTS_URL = "https://world.openfoodfacts.org/api/v0/product/{ean}.json";
    private final RestTemplate restTemplate;

    public OpenFoodFactsService() {
        this.restTemplate = new RestTemplate();
    }

    public Optional<Ingredient> fetchIngredientFromOpenFoodFacts(String ean13) {
        try {
            String url = UriComponentsBuilder.fromUriString(OPENFOODFACTS_URL)
                    .buildAndExpand(ean13)
                    .toUriString();

            OpenFoodFactsResponse response = restTemplate.getForObject(url, OpenFoodFactsResponse.class);

            if (response != null && response.getStatus() == 1) {
                Ingredient ingredient = getIngredient(ean13, response);

                return Optional.of(ingredient);
            }

        } catch (Exception e) {
            log.error("Error fetching from OpenFoodFacts for EAN: {}", ean13, e);
        }
        return Optional.empty();
    }

    private Ingredient getIngredient(String ean13, OpenFoodFactsResponse response) {
        OpenFoodFactsProduct product = response.getProduct();

        Ingredient ingredient = new Ingredient();
        ingredient.setName(product.getProductName());
        ingredient.setProteins(parseDouble(product.getNutriments().getProteins()));
        ingredient.setFats(parseDouble(product.getNutriments().getFat()));
        ingredient.setCarbohydrates(parseDouble(product.getNutriments().getCarbohydrates()));
        ingredient.setCalories(parseDouble(product.getNutriments().getEnergyKcal()));
        ingredient.setQuantity(1.0); // Default quantity
        ingredient.setUnit(Ingredient.Unit.UNIT); // Default unit
        ingredient.setEan13(ean13);
        return ingredient;
    }

    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}
