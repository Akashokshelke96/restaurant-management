package entities;

public class Sales {
    private double amount;
    private Order order;

    public Sales(Order order, double amount ) {
        this.amount = amount;
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    @Override
    public String toString(){
        return "Recipe name = " + this.getOrder().getRecipe().getName() + "Amount is " + this.getAmount();
    }
}
// sales class to get amount and order