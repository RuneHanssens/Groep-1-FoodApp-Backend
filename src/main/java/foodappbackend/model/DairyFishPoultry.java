package foodappbackend.model;

public class DairyFishPoultry extends Category {
    private boolean outdoor = false;
    private Type type = Type.DAIRY;

    public enum Type {
        FISH("Vis", 100),
        DAIRY("Zuivelproducten", 50),
        POULTRY("Gevogelte", 100);
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

    public DairyFishPoultry(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.setType(type);
        this.setOutdoor(outdoor);
    }


    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
        this.setPoints();
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        if (outdoor){
            this.points = this.type.getPoints() - 100;
        } else {
            this.points = this.type.getPoints();
        }
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.DAIRYFISHPOULTRY;
    }
}