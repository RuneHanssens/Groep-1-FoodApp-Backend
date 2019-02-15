package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class Snack extends Category {
    private Type type = Type.ALCOHOL;
    private Amount amount = Amount.EENBEETJE;
    private boolean outdoors = false;

    public enum Amount {
        EENHEELKLEINBEETJE("Een heel klein beetje", 25),
        EENBEETJE("Een beetje", 50),
        WELWAT("Wel wat", 75),
        VEEL("Veel", 100);

        private final String fullName;
        private final int points;

        Amount(String fullName, int points) {
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
    public enum Type {
        ALCOHOL,
        FRISDRANK,
        SNOEP,
        FASTFOOD
    }

    public Snack(Boolean outdoor, Type type, Amount amount) {
        this.setEnumCategory();
        this.setType(type);
        this.setAmount(amount);
        this.setOutdoors(outdoor);
    }

    @Override
    protected void setPoints() {
        this.points = amount.points;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        this.setPoints();
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
        this.setPoints();
    }

    public boolean isOutdoors() {
        return outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
        this.setPoints();
    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.SNACK;
    }
}