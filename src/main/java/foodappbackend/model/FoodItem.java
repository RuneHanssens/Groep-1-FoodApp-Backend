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

    protected int points;
    public FoodItem(){ }

    public int getPoints() {
        return points;
    }
    protected abstract void setPoints();

    public abstract String getReadableString();
}

/*
public interface FoodItem {

    EnumCategory enumCategory = null;
    int points = 50;

    public int getPoints();

    abstract void setPoints();
    abstract void setEnumCategory();


}*/
