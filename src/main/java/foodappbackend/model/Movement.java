package foodappbackend.model;

public class Movement extends Category {
    private Type type;

    public enum Type {
        LOPEN("Lopen"),
        WANDELEN("Wandelen"),
        FIETSEN("Fietsen"),
        ZWEMMEN("Zwemmen"),
        ANDERS("Anders");
        private final String fullName;

        Type(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return this.fullName;
        }
    }

    public Movement() { }

    public Movement(Type type) {
        this.setEnumCategory();
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setPoints() {

    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.MOVEMENT;
    }
}