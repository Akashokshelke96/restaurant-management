package entities;


import java.util.Map;

public class Recipe {
    public Recipe(String name, Double amount, Map<Ingredient, Double> composition) {
        this.name = name;
        this.amount = amount;
        this.composition = composition;
    }

    private String name;
    private Double amount;
    private Map<Ingredient , Double> composition;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Map<Ingredient, Double> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Double> composition) {
        this.composition = composition;
    }
}
