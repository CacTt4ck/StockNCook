package com.tcaputi.back.stockncook.recipe.exception;

public class NotEnoughIngredientException extends RuntimeException {

    public NotEnoughIngredientException(String message) {
        super(message);
    }

    public NotEnoughIngredientException(String message, Throwable cause) {
        super(message, cause);
    }
}
