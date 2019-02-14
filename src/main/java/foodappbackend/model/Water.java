package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Water extends Category {

    private int amount = 1;

    public Water(){
        this.setEnumCategory();
        this.setPoints();
    }

    public Water(int amount){
        setAmount(amount);
        this.setEnumCategory();
        this.setPoints();
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    protected void setPoints() {
        points = 50;
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory=EnumCategory.WATER;
    }
}
