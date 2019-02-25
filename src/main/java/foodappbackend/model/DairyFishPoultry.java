package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class DairyFishPoultry extends FoodItem {
    private boolean outdoors;
    private Type type = Type.DAIRY;
    private SubType subType;
    private int amount = 0; // Only used for eggs and milk...

    public DairyFishPoultry() { }

    public boolean isOutdoors() {
        return outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
    }

    public enum Type {
        @JsonProperty("Vis")
        FISH("Vis", 20),
        @JsonProperty("Zuivelproducten")
        DAIRY("Zuivelproducten"),
        @JsonProperty("Kip of Kalkoen")
        CHICKEN("Kip", 20),
        @JsonProperty("Eieren")
        EGG("Ei",20);
        private final String FULLNAME;
        private final int POINTS;

        Type(String fullName) {
            this.FULLNAME = fullName;
            this.POINTS = 0;
        }

        Type(String fullName, int points) {
            this.FULLNAME = fullName;
            this.POINTS = points;
        }

        public int getPoints(){
            return POINTS;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public enum SubType {
        @JsonProperty("Hollandse Kaas")
        DUTCHCHEESE("Hollandse Kaas",50),
        @JsonProperty("Smeerkaas")
        CHEESESPREAD("Smeerkaas, Platte Kaas of Kruidenkaas",30),
        @JsonProperty("Melk")
        MILK("Melk",20),
        @JsonProperty("Natuur Yoghurt")
        NATURALYOGHURT("Natuuryoghurt",20),
        @JsonProperty("Andere Yoghurt")
        OTHERYOGHURT("Andere Yoghurt",45);
        private final String FULLNAME;
        private final int POINTS;

        SubType(String fullName, int points) {
            this.FULLNAME = fullName;
            this.POINTS = points;
        }

        public int getPoints() {
            return this.POINTS;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public DairyFishPoultry(Type type) {
        this.setType(type);
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        if(type == Type.EGG) points = type.getPoints() * amount;
        else if(subType != null && subType == SubType.MILK) points = subType.getPoints() * amount;
        else {
            if(subType != null) this.points = subType.getPoints();
            else this.points = type.getPoints();
        }
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
        this.setPoints();
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.setPoints();
    }

    public SubType getSubType() {
        return this.subType;
    }

    public int getAmount() {
        return this.amount;
    }
}