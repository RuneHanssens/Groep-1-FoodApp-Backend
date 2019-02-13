package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
@Entity
@Table(name = "day_table")
public class Day {
    @Id
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate creationDate;

//    private MovementRepository movementRepository;
//    private WaterRepository waterRepository;
//    private FruitRepository fruitRepository;
//    private VegetableRepository vegetableRepository;
//    private DairyFishPoultryRepository dairyFishPoultryRepository;
//    private FattyRepository fattyRepository;
//    private NutsRepository nutsRepository;
//    private StarchRepository starchRepository;
//    private SnackRepository snackRepository;

    public Day() {
        creationDate = LocalDate.now();
//        movementRepository = new MovementRepository();
//        waterRepository = new WaterRepository();
//        fruitRepository = new FruitRepository();
//        vegetableRepository = new VegetableRepository();
//        dairyFishPoultryRepository = new DairyFishPoultryRepository();
//        fattyRepository = new FattyRepository();
//        nutsRepository = new NutsRepository();
//        starchRepository = new StarchRepository();
//        snackRepository = new SnackRepository();
    }

    public Day(LocalDate localDate) {
        creationDate = localDate;
//        movementRepository = new MovementRepository();
//        waterRepository = new WaterRepository();
//        fruitRepository = new FruitRepository();
//        vegetableRepository = new VegetableRepository();
//        dairyFishPoultryRepository = new DairyFishPoultryRepository();
//        fattyRepository = new FattyRepository();
//        nutsRepository = new NutsRepository();
//        starchRepository = new StarchRepository();
//        snackRepository = new SnackRepository();
    }

    public LocalDate getDate() {
        return creationDate;
    }

//    public LocalDate getCreationDate() {
//        return creationDate;
//    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

//    public MovementRepository getMovementRepository() {
//        return movementRepository;
//    }
//
//    public void setMovementRepository(MovementRepository movementRepository) {
//        this.movementRepository = movementRepository;
//    }
//
//    public WaterRepository getWaterRepository() {
//        return waterRepository;
//    }
//
//    public void setWaterRepository(WaterRepository waterRepository) {
//        this.waterRepository = waterRepository;
//    }
//
//    public FruitRepository getFruitRepository() {
//        return fruitRepository;
//    }
//
//    public void setFruitRepository(FruitRepository fruitRepository) {
//        this.fruitRepository = fruitRepository;
//    }
//
//    public VegetableRepository getVegetableRepository() {
//        return vegetableRepository;
//    }
//
//    public void setVegetableRepository(VegetableRepository vegetableRepository) {
//        this.vegetableRepository = vegetableRepository;
//    }
//
//    public DairyFishPoultryRepository getDairyFishPoultryRepository() {
//        return dairyFishPoultryRepository;
//    }
//
//    public void setDairyFishPoultryRepository(DairyFishPoultryRepository dairyFishPoultryRepository) {
//        this.dairyFishPoultryRepository = dairyFishPoultryRepository;
//    }
//
//    public FattyRepository getFattyRepository() {
//        return fattyRepository;
//    }
//
//    public void setFattyRepository(FattyRepository fattyRepository) {
//        this.fattyRepository = fattyRepository;
//    }
//
//    public NutsRepository getNutsRepository() {
//        return nutsRepository;
//    }
//
//    public void setNutsRepository(NutsRepository nutsRepository) {
//        this.nutsRepository = nutsRepository;
//    }
//
//    public StarchRepository getStarchRepository() {
//        return starchRepository;
//    }
//
//    public void setStarchRepository(StarchRepository starchRepository) {
//        this.starchRepository = starchRepository;
//    }
//
//    public SnackRepository getSnackRepository() {
//        return snackRepository;
//    }
//
//    public void setSnackRepository(SnackRepository snackRepository) {
//        this.snackRepository = snackRepository;
//    }
}
