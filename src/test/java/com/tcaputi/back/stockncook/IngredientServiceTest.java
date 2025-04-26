package com.tcaputi.back.stockncook;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tcaputi.back.stockncook.ingredient.Ingredient;
import com.tcaputi.back.stockncook.ingredient.IngredientException;
import com.tcaputi.back.stockncook.ingredient.IngredientRepository;
import com.tcaputi.back.stockncook.ingredient.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Ingredient createSkyrIngredient() {
        return new Ingredient(
                1L,
                "Skyr",
                6.0, 0.2, 3.6, 60.0,
                2400.0, // quantity = (5*450)+150
                "3329770077003",
                Ingredient.Unit.GRAM,
                5, // 5 pots
                150.0, // 150g open
                450.0  // 450g per pot
        );
    }

    @Test
    void testConsumeIngredient_NormalConsumption() {
        Ingredient skyr = createSkyrIngredient();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(skyr));
        when(ingredientRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ingredient updated = ingredientService.consumeIngredient(1L, 100.0);

        assertEquals(2300.0, updated.getQuantity(), 0.01);
        assertEquals(50.0, updated.getOpenQuantity(), 0.01);
        assertEquals(5, updated.getUnitStock());
    }

    @Test
    void testConsumeIngredient_ConsumptionThatOpensNewUnit() {
        Ingredient skyr = createSkyrIngredient();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(skyr));
        when(ingredientRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ingredient updated = ingredientService.consumeIngredient(1L, 200.0);

        assertEquals(2200.0, updated.getQuantity(), 0.01);
        assertEquals(400.0, updated.getOpenQuantity(), 0.01);
        assertEquals(4, updated.getUnitStock());
    }

    @Test
    void testConsumeIngredient_NotEnoughStock_ShouldThrow() {
        Ingredient skyr = createSkyrIngredient();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(skyr));

        IngredientException exception = assertThrows(IngredientException.class, () ->
                ingredientService.consumeIngredient(1L, 3000.0)
        );

        assertTrue(exception.getMessage().contains("Not enough stock"));
    }

    @Test
    void testConsumeIngredient_FullConsumption() {
        Ingredient skyr = createSkyrIngredient();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(skyr));
        when(ingredientRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ingredient updated = ingredientService.consumeIngredient(1L, 2400.0);

        assertEquals(0.0, updated.getQuantity(), 0.01);
        assertEquals(0.0, updated.getOpenQuantity(), 0.01);
        assertEquals(0, updated.getUnitStock());
    }
}
