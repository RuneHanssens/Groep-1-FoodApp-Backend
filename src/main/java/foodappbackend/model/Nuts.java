package foodappbackend.model;

public class Nuts extends Category {
    private boolean outdoor = false;

    public Nuts(){};

    public Nuts(boolean outdoor) {
        this.setEnumCategory();
        this.setOutdoor(outdoor);
    }

    @Override
    protected void setPoints() {
        this.points = 50;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
        this.setPoints();
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.NUTS;
    }
}