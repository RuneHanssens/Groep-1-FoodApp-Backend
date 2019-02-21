package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class FattyFood extends FoodItem {
    private Type type = Type.BUTTER;

    public FattyFood() { }

    public enum Type {
        @JsonProperty("Boter")
        BUTTER("Boter", 20),
        @JsonProperty("Rood Vlees")
        REDMEAT("Vlees", 40),
        @JsonProperty("Bewerkt Vlees")
        PROCESSEDMEAT("Bewerkt Vlees",80);
        private final String FULLNAME;
        private final int POINTS;

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

    public FattyFood(boolean outdoor, Type type) {
        this.setType(type);
    }

    @Override
    protected void setPoints() {
        this.points = type.getPoints();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.setPoints();
    }
}