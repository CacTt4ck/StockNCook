package com.tcaputi.back.stockncook;

import com.tcaputi.back.stockncook.ingredient.model.Ingredient;
import com.tcaputi.back.stockncook.ingredient.model.Unit;
import com.tcaputi.back.stockncook.ingredient.service.IngredientService;
import com.tcaputi.back.stockncook.recipe.model.Recipe;
import com.tcaputi.back.stockncook.recipe.model.RecipeDto;
import com.tcaputi.back.stockncook.recipe.repository.RecipeRepository;
import com.tcaputi.back.stockncook.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Ingredient createBanana() {
        return new Ingredient(
                1L,
                "Banana",
                1.1, 0.3, 22.8, 89.0,
                500.0,
                "1111111111111",
                Unit.GRAM,
                1,
                50.0,
                450.0
        );
    }

    private Ingredient createMilk() {
        return new Ingredient(
                2L,
                "Milk",
                3.4, 3.6, 5.0, 60.0,
                1000.0,
                "2222222222222",
                Unit.MILLILITER,
                1,
                0.0,
                1000.0
        );
    }

    @Test
    void testCreateAndPrepareRecipe() {
        // Arrange - préparer ingrédients et mocks
        Ingredient banana = createBanana();
        Ingredient milk = createMilk();

        when(ingredientService.getIngredientById(1L)).thenReturn(banana);
        when(ingredientService.getIngredientById(2L)).thenReturn(milk);
        when(recipeRepository.save(any())).thenAnswer(invocation -> {
            Recipe saved = invocation.getArgument(0);
            if (saved.getId() == null) {
                saved.setId(1L); // simulate DB auto-generating ID
            }
            return saved;
        });


        // Créer DTO de recette
        RecipeDto recipeDto = new RecipeDto(
                "Banana Smoothie",
                "Delicious smoothie full of energy.",
                "1. Blend bananas and milk.\n2. Serve fresh.",
                List.of(
                        new RecipeDto.RecipeIngredientDto(1L, 200.0),
                        new RecipeDto.RecipeIngredientDto(2L, 300.0)
                )
        );

        // Act - créer la recette
        Recipe createdRecipe = recipeService.createRecipe(recipeDto);

        // Assert création
        assertEquals("Banana Smoothie", createdRecipe.getName());
        assertEquals(2, createdRecipe.getIngredients().size());
        assertEquals("Delicious smoothie full of energy.", createdRecipe.getDescription());
        assertEquals("1. Blend bananas and milk.\n2. Serve fresh.", createdRecipe.getInstructions());

        // Préparer le simulateur de consommation
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(createdRecipe));

        // Simulation de préparation - doit réussir (assez de stock)
        assertDoesNotThrow(() -> recipeService.simulateRecipePreparation(createdRecipe.getId()));

        // Test de préparation réelle (consommation)
        // mock consumeIngredient appelé 2 fois
        when(ingredientService.consumeIngredient(anyLong(), anyDouble()))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    Double amount = invocation.getArgument(1);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(id);
                    // tu peux ajouter plus de champ si tu veux
                    return ingredient;
                });


        assertDoesNotThrow(() -> recipeService.prepareRecipe(createdRecipe.getId()));

        verify(ingredientService, times(2)).consumeIngredient(anyLong(), anyDouble());
    }

    @Test
    void testMissingIngredients() {
        Ingredient banana = createBanana();
        banana.setQuantity(100.0); // pas assez de banane

        Ingredient milk = createMilk();

        when(ingredientService.getIngredientById(1L)).thenReturn(banana);
        when(ingredientService.getIngredientById(2L)).thenReturn(milk);

        RecipeDto recipeDto = new RecipeDto(
                "Banana Smoothie",
                "Yummy",
                "Blend.",
                List.of(
                        new RecipeDto.RecipeIngredientDto(1L, 200.0),
                        new RecipeDto.RecipeIngredientDto(2L, 300.0)
                )
        );

        when(recipeRepository.save(any())).thenAnswer(invocation -> {
            Recipe savedRecipe = invocation.getArgument(0);
            if (savedRecipe.getId() == null) {
                savedRecipe.setId(1L); // Force un ID simulé
            }
            return savedRecipe;
        });

        Recipe createdRecipe = recipeService.createRecipe(recipeDto);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(createdRecipe));

        List<String> missing = recipeService.getMissingIngredients(createdRecipe.getId());

        assertEquals(1, missing.size());
        assertTrue(missing.contains("Banana"));
    }
}