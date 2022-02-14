package exceptions;

import entities.Ingredient;

import java.util.Map;

public class InsufficientIngredientException extends RuntimeException{
    private Map<Ingredient,Double> insufficientIngredients;


    public InsufficientIngredientException(Map<Ingredient, Double> insufficientIngredients) {
     this.insufficientIngredients = insufficientIngredients;
    }

    public Map<Ingredient, Double> getInsufficientIngredients() {
        return insufficientIngredients;
    }
}
