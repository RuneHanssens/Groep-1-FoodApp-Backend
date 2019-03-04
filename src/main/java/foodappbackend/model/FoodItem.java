package foodappbackend.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FoodItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    protected int points;
    public FoodItem(){ }

    public int getPoints() {
        return points;
    }
    protected abstract void setPoints();

    public abstract String getReadableString();
}