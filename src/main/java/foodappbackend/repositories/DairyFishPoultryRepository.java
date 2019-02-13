package foodappbackend.repositories;

import javax.persistence.Entity;
import javax.persistence.Id;


public class DairyFishPoultryRepository extends FoodRepository {

    @Override
    protected int getMaximumAllowed() {
        return 0;
    }

    @Override
    protected int getMinimumRequired() {
        return 0;
    }
}
