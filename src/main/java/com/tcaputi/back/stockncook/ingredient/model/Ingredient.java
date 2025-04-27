package com.tcaputi.back.stockncook.ingredient.model;

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

    private Double quantity;        // total quantity for recipe usage (openQuantity + unitStock * unitWeight)

    @Column(unique = true)
    private String ean13;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private Integer unitStock;      // number of full units (e.g., 5 pots)
    private Double openQuantity;    // quantity of an open unit (e.g., 150g left)
    private Double unitWeight;      // weight of one unit (e.g., 450g for a Skyr pot)

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
