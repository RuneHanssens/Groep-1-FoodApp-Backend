package foodappbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.UUID;

@Entity
public class Vegetable extends Category {

    private boolean outdoors;
    private boolean fiftyplus;
    public Vegetable(){

    }

    public Vegetable(boolean outdoors, boolean fiftyplus ){
        this.setEnumCategory();
        this.outdoors = outdoors;
        this.fiftyplus = fiftyplus;
        this.setPoints();
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.VEGETABLE;
    }

    @Override
    protected void setPoints() {
        if (outdoors){
            points = 20;
        } else if (fiftyplus){
            points = 70;
        } else {
            points = 40;
        }
    }
}

/*public class Vegetable implements Category {
    private long points = 50;
    private EnumCategory enumCategory;
    private boolean outdoors;
    private boolean fiftyplus;

    public Vegetable(boolean outdoors, boolean fiftyplus ){
        this.setEnumCategory();
        this.outdoors = outdoors;
        this.fiftyplus = fiftyplus;
        this.setPoints();
    }

    @Override
    public void setEnumCategory() {
        this.enumCategory = EnumCategory.VEGETABLE;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void setPoints() {
        if (outdoors){
            points = 20;
        } else if (fiftyplus){
            points = 70;
        } else {
            points = 40;
        }
    }
}*/