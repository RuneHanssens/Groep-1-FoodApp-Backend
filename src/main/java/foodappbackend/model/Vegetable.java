package foodappbackend.model;

public class Vegetable extends Food {
    public Vegetable(boolean outdoor) {
        this.outdoor = outdoor;
        if(outdoor) value = 20;
        else value = 50;
    }
}
