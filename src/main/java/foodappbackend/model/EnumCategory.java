package foodappbackend.model;

import javax.persistence.Entity;

public enum EnumCategory {
    VEGETABLE(100,1000),
    WATER(100,1000),
    SNACK(0, 1000),
    STARCHPRODUCT(100, 1000),
    NUTS(100, 1000),
    MOVEMENT(100, 1000),
    FRUIT(100, 1000),
    FATTYFOOD(100, 1000),
    DAIRYFISHPOULTRY(100, 1000);

    private int min, max;
    private EnumCategory(int min, int max){
        setMin(min);
        setMax(max);
    }

    public int getMin() {
        return min;
    }
    private void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    private void setMax(int max) {
        this.max = max;
    }
}
