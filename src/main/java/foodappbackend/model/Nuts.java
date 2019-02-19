package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Nuts extends Category {

    public Nuts() {
        this.setEnumCategory();
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        this.points = 50;
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.NUTS;
    }
}