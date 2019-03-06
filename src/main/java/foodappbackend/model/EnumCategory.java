package foodappbackend.model;

import javax.persistence.Entity;

public enum EnumCategory {
    VEGETABLE(100,400),
    WATER(100,1000),
    SNACK(0, 20),
    STARCHPRODUCT(20, 110),
    NUTS(0, 110),
    MOVEMENT(100, 1000),
    FRUIT(100, 400),
    FATTYFOOD(0, 50),
    DAIRYFISHPOULTRY(40, 110);

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
