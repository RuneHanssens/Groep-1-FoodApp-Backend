package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Nuts extends FoodItem {
    private boolean salted, outdoors;

    public Nuts() {
        this.setPoints();
    }
    public Nuts(Boolean salted) {
        this.setSalted(salted);
    }

    public boolean getSalted() {
        return this.salted;
    }

    private void setSalted(boolean salted) {
        this.salted = salted;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        if(salted)
            this.points = 50;
        else
            this.points = 20;
    }

    public boolean isOutdoors() {
        return outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
    }
}