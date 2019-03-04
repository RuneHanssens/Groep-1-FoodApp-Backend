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
        ALCOHOL(20, "Alcohol"),
        @JsonProperty("Frisdrank")
        FRISDRANK("Frisdank"),
        @JsonProperty("Zoetigheid")
        SWEETS("Zoetigheid"),
        @JsonProperty("Fastfood")
        FASTFOOD(40, "Fastfood"),
        @JsonProperty("Saus")
        SAUS(10, "Saus");
        private int points;
        private final String FULLNAME;

        Type(int points, String fullName) {
            this.points = points;
            this.FULLNAME = fullName;
        }

        Type(String fullName) {
            this.FULLNAME = fullName;
        }

        public int getPoints() {
            return this.points;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public enum SubType {
        @JsonProperty("Snoep")
        CANDY(30, "Snoep"),
        @JsonProperty("Chocolade")
        CHOCOLATE(40, "Chocolade"),
        @JsonProperty("Koekjes")
        COOKIES(30, "Koekjes"),
        @JsonProperty("Light")
        LIGHT(10, "Light"),
        @JsonProperty("Gewoon")
        NORMAL(20, "Gewoon"),
        @JsonProperty("Gebak")
        PASTRY(40, "Gebak");
        private int POINTS;
        private final String FULLNAME;

        SubType(int points, String fullName) {
            this.POINTS = points;
            this.FULLNAME = fullName;
        }

        public int getPoints() {
            return this.POINTS;
        }

        public String toString() {
            return this.FULLNAME;
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
        return this.getType().toString() + (this.getSubType() != null ? ", " + this.getSubType().toString() : "");
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