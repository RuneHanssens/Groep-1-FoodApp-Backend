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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class StarchProduct extends FoodItem {
    private int amount;
    private boolean outdoor = false;
    private Type type = Type.AARDAPPELEN;
    private SubType subType;

    public StarchProduct(){}

    public enum Type {
        @JsonProperty("Brood")
        BROOD("Brood"),
        @JsonProperty("Rijst")
        RIJST("Rijst"),
        @JsonProperty("Aardappelen")
        AARDAPPELEN("Aardappelen"),
        @JsonProperty("Pasta")
        PASTA("Pasta"),
        @JsonProperty("Cornflakes")
        CORNFLAKES("Cornflakes", 55),
        @JsonProperty("Granola of Havermout")
        GRANOLA("Granola", 20),
        @JsonProperty("Wrap")
        WRAP("Wrap");
        private final String FULLNAME;
        private int points = 0;

        Type(String fullName) {
            this.FULLNAME = fullName;
        }

        Type(String fullName, int points) {
            this.FULLNAME = fullName;
            this.points = points;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public enum SubType {
        @JsonProperty("Wit")
        WIT("Wit",55),        // Wit Brood, Witte Rijst, Witte Pasta
        @JsonProperty("Volkoren")
        VOLKOREN("Volkoren",20),   // Volkorenrijst, Volkorenpasta
        @JsonProperty("Donker")
        DONKER("Donker",20),     // Donker Brood
        @JsonProperty("Gekookt")
        GEKOOKT("Gekookt",20),    // Gekookte Aardappelen
        @JsonProperty("Gebakken")
        GEBAKKEN("Gebakken",40),   // Gebakken Aardappelen
        @JsonProperty("Gratin")
        GRATIN("Gratin",40),     // Aardappelgratin
        @JsonProperty("Frieten")
        FRIETEN("Frieten",55),    // Frieten
        @JsonProperty("Broodje")
        BROODJE("Broodje", 55);
        private final String FULLNAME;
        private final int POINTS;

        SubType(String fullName, int points) {
            this.FULLNAME = fullName;
            this.POINTS = points;
        }

        public int getPoints() {
            return this.POINTS;
        }

        @Override
        public String toString() {
            return this.FULLNAME;
        }
    }

    public StarchProduct(boolean outdoor, Type type) {
        this.setOutdoor(outdoor);
        this.setType(type);
    }

    @Override
    protected void setPoints() {
        if(subType == null)
            this.points = outdoor ? type.points + 10 : type.points;
        else this.points = outdoor ? subType.POINTS + 10 : subType.POINTS;
    }

    @Override
    public String getReadableString() {
        return (this.getType() == Type.BROOD && (this.subType == SubType.WIT || this.subType == SubType.DONKER)? "1 sneetje " : "") + this.getType().toString() + (this.subType != null ? ", "+ this.subType.toString() : "") + (this.outdoor ? ", buitenshuis" : "");
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

    public void setSubType(SubType subType) {
        this.subType = subType;
        this.setPoints();
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}