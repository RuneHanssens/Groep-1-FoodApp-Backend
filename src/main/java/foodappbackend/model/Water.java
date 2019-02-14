package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Water extends Category {


    public Water(){
        this.setEnumCategory();
        this.setPoints();
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
