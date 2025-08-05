package com.tcaputi.back.stockncook.ingredient.controller;

import com.tcaputi.back.stockncook.ingredient.model.ConsumeIngredientDto;
import com.tcaputi.back.stockncook.ingredient.model.Ingredient;
import com.tcaputi.back.stockncook.ingredient.model.IngredientDto;
import com.tcaputi.back.stockncook.ingredient.service.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ingredients", description = "Operations related to ingredients")
@RestController
@AllArgsConstructor
@RequestMapping("/api/ingredients")
class IngredientController {

    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<IngredientDto> getAllIngredients(Pageable pageable) {
        return ingredientService.getAllIngredients(pageable)
                .map(this::convertToDto);
    }

    @GetMapping("/{id}")
    public IngredientDto getIngredientById(@PathVariable Long id) {
        return convertToDto(ingredientService.getIngredientById(id));
    }

    @GetMapping("/ean/{ean13}")
    public IngredientDto getIngredientByEan13(@PathVariable String ean13) {
        return convertToDto(ingredientService.getIngredientByEan13(ean13));
    }

    @PostMapping
    public IngredientDto addIngredient(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = convertToEntity(ingredientDto);
        return convertToDto(ingredientService.addIngredient(ingredient));
    }

    @PutMapping("/{id}")
    public IngredientDto updateIngredient(@PathVariable Long id, @RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = convertToEntity(ingredientDto);
        ingredient.setId(id);
        return convertToDto(ingredientService.updateIngredient(ingredient));
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }

    @PostMapping("/consume")
    public IngredientDto consumeIngredient(@Valid @RequestBody ConsumeIngredientDto dto) {
        Ingredient updatedIngredient = ingredientService.consumeIngredient(dto.getIngredientId(), dto.getAmountInGrams());
        return convertToDto(updatedIngredient);
    }

    private IngredientDto convertToDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    private Ingredient convertToEntity(IngredientDto ingredientDto) {
        return modelMapper.map(ingredientDto, Ingredient.class);
    }
}
