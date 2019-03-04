package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class Snack extends FoodItem {
    private Type type;
    private SubType subType;

    public Snack(){};

    public enum Type {
        @JsonProperty("Alcohol")
        ALCOHOL(20),
        @JsonProperty("Frisdrank")
        FRISDRANK(),
        @JsonProperty("Zoetigheid")
        SWEETS(),
        @JsonProperty("Fastfood")
        FASTFOOD(40),
        @JsonProperty("Saus")
        SAUS(10);
        private final int POINTS;

        Type() {
            this.POINTS = 0;
        }

        Type(int points) {
            this.POINTS = points;
        }

        public int getPoints() {
            return this.POINTS;
        }
    }

    public enum SubType {
        @JsonProperty("Snoep")
        CANDY(30),
        @JsonProperty("Chocolade")
        CHOCOLATE(40),
        @JsonProperty("Koekjes")
        COOKIES(30),
        @JsonProperty("Light")
        LIGHT(10),
        @JsonProperty("Gewoon")
        NORMAL(20),
        @JsonProperty("Gebak")
        PASTRY(40);
        private final int POINTS;

        SubType(int points) {
            this.POINTS = points;
        }

        public int getPoints() {
            return this.POINTS;
        }
    }

    public Snack(Type type, SubType subType) {
        this.setType(type);
        this.setSubType(subType);
    }

    @Override
    protected void setPoints() {
        if(subType != null)
            this.points = subType.getPoints();
        else
            this.points = type.getPoints();
    }

    @Override
    public String getReadableString() {
        return this.getType().toString() + (this.getSubType() != null ? ", " + this.getSubType().toString() + "." : ".");
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.setPoints();
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
        this.setPoints();
    }
}