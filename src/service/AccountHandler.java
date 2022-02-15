package service;

import entities.Expense;
import entities.Sales;

import java.util.List;

public class AccountHandler {
    public void printSales(List<Sales> salesList){
        System.out.println("Total number of  Sales done so far " + salesList.size());
        for(Sales sales : salesList) {
            System.out.println(sales.toString());
        }
    }
    public void printExpenses(List<Expense> expenseList){
        System.out.println("Total number of Expense done so far " + expenseList.size());
        for (Expense expense: expenseList){
            System.out.println(expense.toString());
        }
    }
    public void printProfit(List<Sales> salesList, List<Expense> expenseList ){
        System.out.println("");
        double netProfit = 0;
        for(Sales sales : salesList){
            netProfit += sales.getAmount();
        }
        for(Expense expense : expenseList){
            netProfit -= expense.getAmount();

        }
        System.out.println("Net Profit so far is " + netProfit);

        }
    }
