package io;

import entities.Ingredient;
import entities.Recipe;
import exceptions.InvalidIngredientException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeIo {
    public List<Recipe> readAllRecipes(String filePath, List<Ingredient> ingredientList) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);
        List<Recipe> recipeList = new ArrayList<>();
        for (String line : lines) {
            String[] splitLine = line.split(" ");
            String recipeName = splitLine[0];
            double amount = Double.parseDouble(splitLine[1]);
            Map<Ingredient, Double> composition = new HashMap<>();

            for (int i = 2; i < splitLine.length; i += 2) {
                String ingredientName = splitLine[i];
                double qty = Double.parseDouble(splitLine[i + 1]);


                boolean flag = false;
                for (int j = 0; j < ingredientList.size(); j++) {
                    if (ingredientList.get(j).getName().equals(ingredientName)) {
                        flag = true;
                        composition.put(ingredientList.get(i), qty);

                        break;
                    }
                }
                if (flag == false) {
                    throw new InvalidIngredientException("this ingredient does not exists ");
                }

            }
            Recipe recipe = new Recipe(recipeName, amount, composition);
            recipeList.add(recipe);

        }
        System.out.println("Read " + recipeList.size() + " recipes");
        return recipeList;

    }
}
