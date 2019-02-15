package foodappbackend.model;

public class Movement extends Category {
    private Type type = Type.ANDERS;

    public enum Type {
        ZWEMMEN("Zwemmen", 50),
        LOPEN("Lopen", 50),
        FIETSEN("Fietsen", 50),
        WANDELEN("Wandelen", 50),
        ANDERS("Anders", 50);

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

    public Movement(Type type) {
        this.setEnumCategory();
        this.setType(type);
    }

    @Override
    protected void setPoints() {
        this.points = type.points;
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
        this.enumCategory = EnumCategory.MOVEMENT;
    }
}