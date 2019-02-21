package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class Movement extends FoodItem {
    private Type type = Type.ANDERS;
    private int time = 0;

    public Movement(){}

    public enum Type {
        @JsonProperty("Zwemmen")
        ZWEMMEN("Zwemmen", 50),
        @JsonProperty("Lopen")
        LOPEN("Lopen", 50),
        @JsonProperty("Fietsen")
        FIETSEN("Fietsen", 50),
        @JsonProperty("Wandelen")
        WANDELEN("Wandelen", 50),
        @JsonProperty("Anders")
        ANDERS("Anders", 50);

        private final String FULLNAME;
        private final int points;

        Type(String fullName, int points) {
            this.FULLNAME = fullName;
            this.points = points;
        }
        public int getPoints(){
            return points;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public Movement(Type type, int time) {
        this.setType(type);
        this.setTime(time);
    }

    private void setTime(int time) {
        this.time = time;
        this.setPoints();
    }

    public int getTime() {
        return this.time;
    }

    @Override
    protected void setPoints() {
        this.points = type.points;
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }
}