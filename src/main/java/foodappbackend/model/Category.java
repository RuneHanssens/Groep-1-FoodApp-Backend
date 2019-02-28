package foodappbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

//@Entity
public class Category<E extends FoodItem> extends ArrayList<E> implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID uuid;
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }
//    public UUID getUuid() {
//        return uuid;
//    }


    private int totalPoints, min, max;
    public Category(){

    }
    public Category(int min, int max) {
        this.min = min;
        this.max = max;
        this.totalPoints = 0;
    }

    @Override
    public boolean add(E e) {
        this.totalPoints += e.getPoints();
        return super.add(e);
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public boolean getOverMin() {
        return totalPoints >= min;
    }

    public boolean getOverMax() {
        return totalPoints >= max;
    }

    public void removeLast() {
        if(this.size() > 0) {
            this.totalPoints -= this.get(this.size() - 1).getPoints();
            super.remove(this.size() - 1);
        }
    }
}