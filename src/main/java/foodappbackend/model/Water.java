package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Water extends FoodItem {
    private int amount = 1;

    public Water(){};

    public Water(int amount){
        this.setAmount(amount);
    }

    @Override
    protected void setPoints() {
        points = 20 * amount;
    }

    @Override
    public String getReadableString() {
        return amount + " glazen water.";
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setPoints();
    }

    public int getAmount() {
        return amount;
    }
}
