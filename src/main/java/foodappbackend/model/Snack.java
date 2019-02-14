package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Snack extends Category {
    private Type type;
    private Amount amount;
    private boolean outdoors;

    public enum Type {
        ALCOHOL,
        FRISDRANK,
        SNOEP,
        FASTFOOD
    }
    public enum Amount {
        EENHEELKLEINBEETJE,
        EENBEETJE,
        WELWAT,
        VEEL
    }

    public Snack() { }

    public Snack(Type type, Amount amount) {
        this.setEnumCategory();
        this.type = type;
        this.amount = amount;
        this.setPoints();
    }

    @Override
    protected void setPoints() {
        switch(amount) {
            case EENHEELKLEINBEETJE:
                points = 50;
                break;
            case EENBEETJE:
                points = 100;
                break;
            case WELWAT:
                points = 150;
                break;
            case VEEL:
                points = 200;
        }
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.SNACK;
    }
}