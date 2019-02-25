package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Vegetable extends FoodItem {
    private boolean more, outdoors;

    public Vegetable(){}

    public Vegetable(boolean more){
        this.setMore(more);
    }

    @Override
    protected void setPoints() {
        if(more)
            this.points = 100;
        else
            this.points = 50;
        if(outdoors) this.points -= 20;
    }

    public boolean getMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
        this.setPoints();
    }

    public boolean getOutdoors() {
        return this.outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
        this.setPoints();
    }
}