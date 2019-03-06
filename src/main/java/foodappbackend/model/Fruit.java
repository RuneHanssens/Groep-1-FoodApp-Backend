package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Fruit extends FoodItem {
    private int amount = 1;
    private boolean outdoor = false;

    public Fruit() { }

    public Fruit(boolean outdoor, int amount) {
        this.setOutdoor(outdoor);
        this.setAmount(amount);
    }

    @Override
    protected void setPoints() {
        this.points = amount * 50;
    }

    @Override
    public String getReadableString() {
        return this.amount + (this.amount == 1 ? " stuk fruit" : " stukken fruit") + (this.outdoor ? ", buitenshuis" : "");
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setPoints();
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
        this.setPoints();
    }
}