//package foodappbackend.model;
//
//
//public class STARCHPRODUCT extends Food {
//    public enum Type { VOLKORENBROOD, VOLKORENPASTA, VOLKORENRIJST, WITBROOD, WITTEPASTA, WITTERIJST, AARDAPPELEN, GRANOLAHAVERMOUT, CORNFLAKES};
//
//    public STARCHPRODUCT(Type type, boolean outdoor) {
//        switch(type) {
//            case VOLKORENBROOD:
//                value = 20; break;
//            case VOLKORENPASTA:
//                value = 20; break;
//            case VOLKORENRIJST:
//                value = 20; break;
//            case WITTEPASTA:
//                value = 20; break;
//            case WITTERIJST:
//                value = 20; break;
//            case WITBROOD:
//                value = 20; break;
//            case AARDAPPELEN:
//                value = 20; break;
//            case GRANOLAHAVERMOUT:
//                value = 20; break;
//            case CORNFLAKES:
//                value = 20; break;
//            default:
//                value = 500;
//        }
//    }
//}

package foodappbackend.model;

import javax.persistence.Entity;

@Entity
public class StarchProduct extends Category {
    private boolean outdoor = false;
    private Type type = Type.AARDAPPELEN;
    private SubType subType;

    public StarchProduct(){};

    public enum Type {
        WITBROOD("Wit Brood", 20),
        DONKERBROOD("Donker Brood", 70),
        WITTERIJST("Witte Rijst", 20),
        VOLKORENRIJST("Volkorenrijst", 50),
        AARDAPPELEN("Aardappelen", 60),
        WITTEPASTA("Witte Pasta", 20),
        VOLKORENPASTA("Volkorenpasta", 70),
        GRANOLAHAVERMOUT("Granola / Havermout", 70);
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

    public enum SubType {
        
    }

    public StarchProduct(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.setOutdoor(outdoor);
        this.setType(type);
    }

    @Override
    protected void setPoints() {
        if (outdoor) {
            this.points = type.points - 20;
        } else {
            this.points = type.points;
        }

    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
        this.setPoints();
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
        this.enumCategory = EnumCategory.STARCHPRODUCT;
    }
}