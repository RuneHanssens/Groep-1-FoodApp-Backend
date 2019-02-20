/*
package foodappbackend.model;

import java.util.ArrayList;

public class Category<E extends FoodItem> extends ArrayList<E> {
    private int totalPoints, min, max;

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


}
*/