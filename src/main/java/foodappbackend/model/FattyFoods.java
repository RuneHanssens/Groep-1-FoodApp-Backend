package foodappbackend.model;

public class FattyFoods extends Category {
    private boolean outdoor;
    private Type type;

    public enum Type {
        BUTTER("Boter"),
        MEAT("Vlees");
        private final String fullName;

        Type(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return this.fullName;
        }
    }

    public FattyFoods(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.outdoor = outdoor;
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        switch(type) {
            case BUTTER:
                this.points = 20;
                break;
            case MEAT:
                this.points = 80;
        }
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.FATTYFOOD;
    }
}