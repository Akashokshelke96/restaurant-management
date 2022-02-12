package service;

import entities.Ingredient;

import java.util.List;

public class IngredientHandler {
    public void viewIngredients(List<Ingredient> ingredientList){
        for(Ingredient ingredient : ingredientList ){
            System.out.println(ingredient.toString());
        }
    }
    public boolean isPossibleToOrderIngredient(Ingredient ingredient, double qty, double availableMoney){
        if(availableMoney >= ingredient.getRate() * qty){
            return true;
        }
        else{
            return false;

        }
    }
}
