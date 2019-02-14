package foodappbackend.model;

public class Fruit extends Category {
    private int pieces;
    private boolean outdoor;

    public Fruit() { }

    public Fruit(boolean outdoor, int pieces) {
        this.setEnumCategory();
        this.outdoor = outdoor;
        this.pieces = pieces;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        if(outdoor) this.points = 20;
        else this.points = pieces * 40;
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.FRUIT;
    }
}