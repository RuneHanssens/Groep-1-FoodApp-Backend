package foodappbackend.model;

public class Water extends Food {
    public Water(boolean outdoor) {
        this.outdoor = outdoor;
        if(outdoor) value = 20;
        else value = 50;
    }
}
