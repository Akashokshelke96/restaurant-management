import entities.*;
import exceptions.IngredientNotFoundException;
import exceptions.InsufficientIngredientException;
import exceptions.InsufficientMoneyException;
import exceptions.RecipeNotFoundException;
import io.AccountsIo;
import io.IngredientIo;
import io.RecipeIo;
import service.AccountHandler;
import service.IngredientHandler;
import service.RecipeHandler;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static List<Sales> salesList;
    private static List<Expense> expenseList;
    private static double availableMoney;
    private static List<Ingredient> ingredientList;
    private static List<Recipe> recipeList;

    private static RecipeHandler recipeHandler;
    private static AccountHandler accountHandler;
    private static IngredientHandler ingredientHandler;
    private static IngredientIo ingredientIo;
    private static RecipeIo recipeIo;
    private static AccountsIo accountsIo;



    public static void main(String[] args) throws FileNotFoundException {
        salesList = new ArrayList<>();
        expenseList = new ArrayList<>();
        accountHandler = new AccountHandler();
        recipeHandler = new RecipeHandler();
        ingredientIo = new IngredientIo();
        ingredientHandler = new IngredientHandler();
        recipeIo = new RecipeIo();
        accountsIo = new AccountsIo();

        CommandType currentCommand = CommandType.NO_COMMAND;
        Ingredient selectedIngredient = null;
        double ingredientQty = 0;
        double qtyOrdered;
        Recipe selectedRecipe = null;
        Map<Ingredient, Double> insufficientIngredients = null;
        ingredientList =  ingredientIo.readIngredientList("resources\\Ingredients.txt"); //this works on windows
        recipeList = recipeIo.readAllRecipes("resources\\Recipe.txt", ingredientList);
        availableMoney = accountsIo.readAAccounts("resources//Accounts.txt");
        System.out.println("Available Money is " + availableMoney);
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
                        purchaseIngredient(selectedIngredient, ingredientQty);
                        currentCommand = CommandType.NO_COMMAND;
                    } else {
                        throw new InsufficientMoneyException();
                    }
                } else if (currentCommand == CommandType.VIEW_TOTAL_SALES) {
                    accountHandler.printSales(salesList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_TOTAL_EXPENSES) {
                    accountHandler.printExpenses(expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_NET_PROFIT) {
                    accountHandler.printProfit(salesList, expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.PLACE_ORDER) {
                    selectedRecipe = selectRecipe();
                    RecipeHandler.checkIfPossibleToPrepareRecipe(selectedRecipe, ingredientList);
                    currentCommand = CommandType.FINALISE_ORDER;

                } else if (currentCommand == CommandType.ORDER_MULTIPLE_INGREDIENT) {
                    ingredientHandler.isPossibleToOrderIngredients(insufficientIngredients, availableMoney);
                    purchaseIngredients(insufficientIngredients);
                    currentCommand = CommandType.FINALISE_ORDER;

                } else if (currentCommand == CommandType.FINALISE_ORDER) {
                finalizeOrder(selectedRecipe);
                    System.out.println("Order for  " + selectedRecipe.getName() + " is finalised and ready to Serve Hot.");
                    currentCommand = CommandType.NO_COMMAND;
                }
                if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            } catch (InsufficientIngredientException ex) {
                insufficientIngredients = ex.getInsufficientIngredients();
                currentCommand = CommandType.ORDER_MULTIPLE_INGREDIENT;
            } catch (InsufficientMoneyException ex) {
                System.out.println(ex.getMessage());
                currentCommand = CommandType.NO_COMMAND;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static Ingredient selectIngredient() {
        System.out.println("Please Enter the Ingredient you want to order :");
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

    public static void purchaseIngredient(Ingredient ingredientOrdered, double qtyOrdered) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getName().equals(ingredientOrdered.getName())) {
                double oldQty = ingredientList.get(i).getQty();
                ingredientList.get(i).setQty(oldQty + qtyOrdered);
            }
        }
        double totalAmount = ingredientOrdered.getRate() * qtyOrdered;
        Map<Ingredient, Double> composition = new HashMap<>();
        composition.put(ingredientOrdered, qtyOrdered);
        PurchaseOrder po = new PurchaseOrder(totalAmount, composition);
        expenseList.add(new Expense(totalAmount, po, ExpenseType.PURCHASE));
        availableMoney -= totalAmount;
    }

    public static void purchaseIngredients(Map<Ingredient, Double> ingredientsToOrder) {
        double moneySpent = 0.0;
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientsToOrder.containsKey(ingredientList.get(i))) {
                double oldQty = ingredientList.get(i).getQty();
                double qtyToOrder = ingredientsToOrder.get(ingredientList.get(i));
                moneySpent += moneySpent + qtyToOrder * ingredientList.get(i).getRate();
                ingredientList.get(i).setQty(oldQty + ingredientsToOrder.get(ingredientList.get(i)));
            }
        }
        PurchaseOrder po = new PurchaseOrder(moneySpent, ingredientsToOrder);
        Expense expense = new Expense(moneySpent, po, ExpenseType.PURCHASE);
        expenseList.add(expense);
        availableMoney -= moneySpent;

    }

    public static void finalizeOrder(Recipe recipe) {
        Map<Ingredient, Double> composition = recipe.getComposition();
        for (int i = 0; i < ingredientList.size(); i++) {
            Ingredient currentIngredient = ingredientList.get(i);
            if (composition.containsKey(currentIngredient)) {
                double oldQty = currentIngredient.getQty();
                ingredientList.get(i).setQty(oldQty - composition.get(currentIngredient));
            }
        }
        Order order = new Order(recipe, recipe.getAmount());
        Sales sale = new Sales(order , recipe.getAmount());
        salesList.add(sale);
        availableMoney += recipe.getAmount();
    }


    public static Recipe selectRecipe() {
        System.out.println("Please Enter the recipe you want to order");
        Scanner scanner = new Scanner(System.in);
        String recipeName = scanner.nextLine();

        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getName().equals(recipeName)) {
                return recipeList.get(i);

            }
        }
        throw new RecipeNotFoundException("Recipe " + recipeName + " not found");
    }


}
