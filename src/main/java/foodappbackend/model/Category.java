package foodappbackend.model;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.UUID;

@Entity
public abstract class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    protected EnumCategory enumCategory;
    protected int points;

    public Category(){
        this.setEnumCategory();
    }
    public int getPoints() {
        return points;
    }

    protected abstract void setPoints();
    protected abstract void setEnumCategory();


}

/*
public interface Category {

    EnumCategory enumCategory = null;
    int points = 50;

    public int getPoints();

    abstract void setPoints();
    abstract void setEnumCategory();


}*/
