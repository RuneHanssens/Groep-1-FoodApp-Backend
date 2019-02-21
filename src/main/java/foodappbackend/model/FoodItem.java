package foodappbackend.model;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public abstract class FoodItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    protected EnumCategory enumCategory;

    protected int points;
    public FoodItem(){
        this.setEnumCategory();
    }

    public int getPoints() {
        return points;
    }
    protected abstract void setPoints();

    protected abstract void setEnumCategory();

    public EnumCategory getEnumCategory() {
        return enumCategory;
    }

    public boolean isOverMax(){
        return this.getPoints() > this.getEnumCategory().getMax();
    }

    public boolean isOverMin(){
        return this.getPoints() >= this.getEnumCategory().getMin();
    }
}

/*
public interface FoodItem {

    EnumCategory enumCategory = null;
    int points = 50;

    public int getPoints();

    abstract void setPoints();
    abstract void setEnumCategory();


}*/
