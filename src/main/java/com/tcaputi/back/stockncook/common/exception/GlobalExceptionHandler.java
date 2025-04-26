package com.tcaputi.back.stockncook.common.exception;

import com.tcaputi.back.stockncook.ingredient.IngredientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IngredientException.class)
    public ResponseEntity<String> ingredientExceptionHandler(IngredientException ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

}
