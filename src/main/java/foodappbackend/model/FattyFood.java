package foodappbackend.model;

public class FattyFood extends Category {
    private boolean outdoor = false;
    private Type type = Type.BUTTER;

    public enum Type {
        BUTTER("Boter", 50),
        MEAT("Vlees", 100);

        private final String fullName;
        private final int points;

        Type(String fullName, int points) {
            this.fullName = fullName;
            this.points = points;
        }
        public int getPoints(){
            return points;
        }
        @Override
        public String toString() {
            return this.fullName;
        }
    }

    public FattyFood(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.setOutdoor(outdoor);
        this.setType(type);
    }

    @Override
    protected void setPoints() {
        if (outdoor){
            this.points = this.getType().points - 20;
        }
        this.points = this.getType().points;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
        this.setPoints();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.FATTYFOOD;
    }
}