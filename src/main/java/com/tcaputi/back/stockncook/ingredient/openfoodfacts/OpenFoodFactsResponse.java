package com.tcaputi.back.stockncook.ingredient.openfoodfacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodFactsResponse {

    private int status;
    private OpenFoodFactsProduct product;

}
