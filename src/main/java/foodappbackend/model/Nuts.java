package foodappbackend.model;

public class Nuts extends Category {
    private boolean outdoor;

    public Nuts() { }

    public Nuts(boolean outdoor) {
        this.setEnumCategory();
        this.outdoor = outdoor;
        this.setPoints();
    }

    @Override
    protected void setPoints() {

    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.NUTS;
    }
}