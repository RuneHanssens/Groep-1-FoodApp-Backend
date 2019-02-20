package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Water extends Category {
    private int amount = 1;

    public Water(){};

    public Water(int amount){
        this.setEnumCategory();
        this.setAmount(amount);
    }

    @Override
    protected void setPoints() {
        points = 20 * amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setPoints();
    }

    public int getAmount() {
        return amount;
    }
    @Override
    protected void setEnumCategory() {
        this.enumCategory=EnumCategory.WATER;
    }
}
