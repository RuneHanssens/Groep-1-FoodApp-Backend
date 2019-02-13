package foodappbackend.repositories;


public class NutsRepository extends FoodRepository {
    @Override
    protected int getMaximumAllowed() {
        return 0;
    }

    @Override
    protected int getMinimumRequired() {
        return 0;
    }
}
