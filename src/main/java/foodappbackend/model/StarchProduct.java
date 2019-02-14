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

public class StarchProduct extends Category {
    private boolean outdoor;
    private Type type;

    public enum Type {
        WITBROOD("Wit Brood"),
        DONKERBROOD("Donker Brood"),
        WITTERIJST("Witte Rijst"),
        VOLKORENRIJST("Volkorenrijst"),
        AARDAPPELEN("Aardappelen"),
        WITTEPASTA("Witte Pasta"),
        VOLKORENPASTA("Volkorenpasta"),
        GRANOLAHAVERMOUT("Granola / Havermout");
        private final String fullName;

        Type(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return this.fullName;
        }
    }

    public StarchProduct() { }

    public StarchProduct(boolean outdoor, Type type) {
        this.setEnumCategory();
        this.outdoor = outdoor;
        this.type = type;
        this.setPoints();
    }

    @Override
    protected void setPoints() {

    }

    @Override
    protected void setEnumCategory() {
        this.enumCategory = EnumCategory.STARCHPRODUCT;
    }
}