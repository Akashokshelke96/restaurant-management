package entities;

import java.util.Map;

public class PurchaseOrder {
    private double totalAmount;
    private Map<Ingredient, Double> composition; // creates a map of ingredient name and quantity to be purchased

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<Ingredient, Double> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Double> composition) {
        this.composition = composition;
    }

    public PurchaseOrder(double totalAmount, Map<Ingredient, Double> composition) {
        this.totalAmount = totalAmount;
        this.composition = composition;
    }
}
