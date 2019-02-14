package foodappbackend.model;

public class DairyFishPoultry extends Category {
    private boolean outdoor;
    private Type type;

    public enum Type {
        FISH("Vis"),
        DAIRY("Zuivelproducten"),
        POULTRY("Gevogelte");
        private final String fullName;

        Type(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return this.fullName;
        }
    }

    public DairyFishPoultry() { }

    public DairyFishPoultry(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.outdoor = outdoor;
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        switch(type) {
            case FISH:
                this.points = 80;
                break;
            case DAIRY:
                this.points = 60;
                break;
            case POULTRY:
                this.points = 80;
        }
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.DAIRYFISHPOULTRY;
    }
}