package foodappbackend.repositories;


import foodappbackend.model.Food;
import java.util.ArrayList;
import java.util.UUID;

public abstract class FoodRepository {
    protected int currentValue = 0;
    protected ArrayList<Food> foods = new ArrayList<>();

    protected void addValue(Food food) {
        foods.add(food);
        currentValue += food.getValue();
    }

    protected int getCurrentValue() {
        return this.currentValue;
    }

    protected abstract int getMaximumAllowed();
    protected abstract int getMinimumRequired();
}
