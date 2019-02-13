package foodappbackend.repositories;

public class VegetableRepository extends FoodRepository {
    @Override
    protected int getMaximumAllowed() {
        return 0;
    }

    @Override
    protected int getMinimumRequired() {
        return 0;
    }
}
