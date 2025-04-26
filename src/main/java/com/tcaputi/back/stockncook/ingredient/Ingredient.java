package com.tcaputi.back.stockncook.ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double calories;

    private Double quantity;

    @Column(unique = true)
    private String ean13;

    @Enumerated(EnumType.STRING)
    private Unit unit;

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

    }

}
