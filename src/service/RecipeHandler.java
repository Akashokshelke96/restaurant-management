package service;

import entities.Ingredient;
import entities.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeHandler {
    public  void checkIfPossibleToPrepareRecipe(Recipe recipe, List<Ingredient> ingredientList){
        Map<Ingredient, Double> composition = recipe.getComposition();
        Map<Ingredient,Double> insufficientIngredientList = new HashMap<>();
    for (Ingredient ing : ingredientList) {
        if(composition.containsKey(ing)){
          double  qtyUsed = composition.get(ing);
            if(qtyUsed > ing.getQty()) {
                insufficientIngredientList.put(ing, qtyUsed - ing.getQty());
            }
            }
        }


        }
    }
