package com.tcaputi.back.stockncook.ingredient.repository;

import com.tcaputi.back.stockncook.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByEan13(String ean13);

}
