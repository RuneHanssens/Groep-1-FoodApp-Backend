package foodappbackend.model;

public enum EnumCategory {
    VEGETABLE(100,1000),
    WATER(100,1000);

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
