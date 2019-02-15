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

    private long min;
    private long max;
    private EnumCategory(long min, long max){
        setMin(min);
        setMax(max);
    }

    public long getMin() {
        return min;
    }
    private void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    private void setMax(long max) {
        this.max = max;
    }
}
