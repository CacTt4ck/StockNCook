package com.tcaputi.back.stockncook.ingredient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Unit {
    // Weight
    GRAM("g"),
    KILOGRAM("kg"),

    // Volume
    MILLILITER("ml"),
    LITER("l"),

    // Cooking
    TEASPOON("tsp"),   // Petite cuillère
    TABLESPOON("tbsp"), // Cuillère à soupe
    CUP("cup"),        // Tasse

    // Quantity
    PIECE("pc"),
    UNIT("unit"),

    // Other
    PINCH("pinch"),      // Pincée
    SLICE("slice");       // Tranche

    private String abbreviation;

    Unit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @JsonCreator
    public static Unit fromAbbreviation(String value) {
        for (Unit unit : values()) {
            if (unit.abbreviation.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + value);
    }

    @JsonValue
    public String getAbbreviation() {
        return abbreviation;
    }

}
