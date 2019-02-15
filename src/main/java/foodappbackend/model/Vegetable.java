package foodappbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.UUID;

@Entity
public class Vegetable extends Category {
    private Type type;

    public enum Type {
        MORE("Meer dan 400", 50),
        LESS("Minder dan 400", 30),
        OUTDOOR("Buitenshuis", 20);

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

    public Vegetable(Type type){
        this.setEnumCategory();
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

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.VEGETABLE;
    }

}