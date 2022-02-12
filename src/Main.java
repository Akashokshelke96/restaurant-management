import entities.*;
import exceptions.IngredientNotFoundException;
import exceptions.InsufficientMoneyException;
import exceptions.RecipeNotFoundException;
import service.AccountHandler;
import service.IngredientHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static List<Sales> salesList;
    private static List<Expense> expenseList;
    private static double availableMoney;
    private static List<Ingredient> ingredientList;
    private static List<Recipe> recipeList;
    private static AccountHandler accountHandler;
    private static IngredientHandler ingredientHandler;


    public static void main(String[] args) {
        CommandType currentCommand = CommandType.NO_COMMAND;
        Ingredient selectedIngredient = null;
        double ingredientQty = 0;
        double qtyOrdered;
        while (true) {
            try {
                if (currentCommand == CommandType.NO_COMMAND) {
                    int selectedNumber = displayPrompt();
                    currentCommand = CommandType.values()[selectedNumber];
                } else if (currentCommand == CommandType.VIEW_AVAILABLE_INGREDIENTS) {
                    ingredientHandler.viewIngredients(ingredientList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.ORDER_SPECIFIC_INGREDIENT) {
                    selectedIngredient = selectIngredient();
                    currentCommand = CommandType.INPUT_INGREDIENT_QTY;
                } else if (currentCommand == CommandType.INPUT_INGREDIENT_QTY) {
                    ingredientQty = inputIngredientQty();
                    if (ingredientHandler.isPossibleToOrderIngredient(selectedIngredient, ingredientQty, availableMoney)) {
                        System.out.println("Ordered Successfully ");
                        updateIngredientQty(selectedIngredient, ingredientQty);
                        currentCommand = CommandType.NO_COMMAND;
                    } else {
                        throw new InsufficientMoneyException();
                    }
                } else if (currentCommand == CommandType.VIEW_TOTAL_SALES) {
                    accountHandler.printSales(salesList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_TOTAL_EXPENSES) {
                    accountHandler.printExpense(expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_NET_PROFIT) {
                    accountHandler.printProfit(salesList, expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                }else if(currentCommand == CommandType.PLACE_ORDER){

                }
                if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            }
            catch (InsufficientMoneyException ex){
                System.out.println(ex);
                currentCommand = CommandType.NO_COMMAND;
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        }

    private static Ingredient selectIngredient() {
        Scanner scan = new Scanner((System.in));
        String ingredientName = scan.nextLine();

        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getName().equals(ingredientName)) {
                return ingredientList.get(i);
            }
        }
        throw new IngredientNotFoundException("Ingredient " + ingredientName + " not found");
    }

    public static double inputIngredientQty() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    public static int displayPrompt() {
        System.out.println("Command #1 : View Available Ingredients");
        System.out.println("Command #2 : Order Specific Ingredients");
        System.out.println("Command #3 : View Total Sales");
        System.out.println("Command #4 : View Total Expenses");
        System.out.println("Command #5 : View Net Profit");
        System.out.println("Command #6 : Place Order");
        System.out.println("Command #7 : Exit Program");
        System.out.println("Your Wish My Command");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static void updateIngredientQty(Ingredient ingredientOrdered, double qtyOrdered){
        for(int i = 0; i< ingredientList.size();i++){
            if(ingredientList.get(i).getName().equals(ingredientOrdered.getName())){
                double oldQty = ingredientList.get(i).getQty();
                ingredientList.get(i).setQty(oldQty + qtyOrdered);
            }
        }
        double totalAmount = ingredientOrdered.getRate()*qtyOrdered;
        Map<Ingredient,Double> composition = new HashMap<>();
        composition.put(ingredientOrdered,qtyOrdered);
        PurchaseOrder po = new PurchaseOrder(totalAmount,composition);
        expenseList.add(new Expense(totalAmount,po,ExpenseType.PURCHASE));

    }
    public static void purchaseIngredient(Ingredient ingredientOrdered , double qty){
        for(int i = 0; i < ingredientList.size() ; i++){
            if(ingredientList.get(i).getName().equals(ingredientOrdered.getName())){
                double oldQty = ingredientList.get(i).getQty();

                double qtyOrdered = 0;
                ingredientList.get(i).setQty(oldQty + qtyOrdered);
            }
        }

    }
    public  static Recipe selectRecipe() {
        Scanner scanner = new Scanner(System.in);
        String recipeName = scanner.nextLine();

        for (int i = 0; i < recipeList.size();i++){
            if(recipeList.get(i).getName().equals(recipeName)){
                return recipeList.get(i);

            }
        }
        throw new RecipeNotFoundException("Recipe " + recipeName + " not found");
    }


}
