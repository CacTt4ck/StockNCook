package com.tcaputi.back.stockncook.ingredient.openfoodfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodFactsProduct {

    @JsonProperty("product_name")
    private String productName;

    private Nutriments nutriments;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Nutriments {
        @JsonProperty("proteins_100g")
        private String proteins;

        @JsonProperty("fat_100g")
        private String fat;

        @JsonProperty("carbohydrates_100g")
        private String carbohydrates;

        @JsonProperty("energy-kcal_100g")
        private String energyKcal;
    }
}
