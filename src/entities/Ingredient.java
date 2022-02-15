package entities;

public class Ingredient {
    public Ingredient(String name, double qty, double rate) {
        this.name = name;
        this.qty = qty;
        this.rate = rate;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    @Override
    public String toString(){
        return "Name = " + this.name + ", Qty = " + this.qty + ", Rate = " + this.rate;

    }
    @Override
    public boolean equals(Object object){
        if(this.getClass() != object.getClass()){
            return false;
        }
        else { Ingredient otherIngredient = (Ingredient) object;
            return this.getName().equals(otherIngredient.getName());
        }
    }

    private double qty;
    private double rate;

}


//this class contains an object of ingredient