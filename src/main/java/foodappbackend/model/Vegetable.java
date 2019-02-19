package foodappbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.UUID;

@Entity
public class Vegetable extends Category {
    private boolean more;

    public Vegetable(){}

    public Vegetable(boolean more){
        this.setEnumCategory();
        this.setMore(more);
    }

    @Override
    protected void setPoints() {
        if(more)
            this.points = 50;
        else
            this.points = 20;
    }

    public boolean getMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
        this.setPoints();
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.VEGETABLE;
    }

}