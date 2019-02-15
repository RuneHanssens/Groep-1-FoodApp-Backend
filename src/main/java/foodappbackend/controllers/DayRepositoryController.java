package foodappbackend.controllers;

import foodappbackend.model.*;
import foodappbackend.repositories.DayRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RestController
public class DayRepositoryController {
    private DayRepository dayRepository;

    public DayRepositoryController(DayRepository dayRepository) {
        this.dayRepository = dayRepository;

    }

    /************************************ DAYS **************************************/
    @RequestMapping(value = "/api/days", method = RequestMethod.GET)
    public Iterable<Day> getDayRepository() {
        return this.dayRepository.findAll();
    }

    /************************************ DAY **************************************/
    @RequestMapping(value = "/api/day", method = RequestMethod.GET)
    public Day getDay(@RequestParam(name = "date", required = false) String date) {

        LocalDate localDate;

        if (date == null || date.trim().isEmpty()) {
            localDate = LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(date, formatter);
        }

        if (dayRepository.findById(localDate).isPresent()) {
            return dayRepository.findById(localDate).get();
        }

        return dayRepository.save(new Day(localDate));
    }


    @RequestMapping(value = "/api/day/points", method = RequestMethod.GET)
    public Map<String, Integer> getDayPoints(@RequestParam(name = "date", required = false) String date) {
        Map<String, Integer> map = new HashMap<>();
        Day day = this.getDay(date);
        for (EnumCategory c : EnumCategory.values()) {
            map.put(c.toString(), day.getPointsCategory(c));
        }
        return map;
    }

    /************************************ VEGETABLES **************************************/
    @RequestMapping(value = "/api/day/vegetable", method = RequestMethod.GET)
    public int getDayVegetableRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.VEGETABLE);
    }
    @RequestMapping(value = "/api/day/vegetable", method = RequestMethod.POST)
    public int putVegetableInDayRepository(@RequestBody Vegetable vegetable, @RequestParam(name = "date", required = false) String date) {
        return this.addToDayRepo(vegetable, date);
    }
    @RequestMapping(value = "/api/day/vegetable/points", method = RequestMethod.GET)
    public int getDayVegetablePoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.VEGETABLE);
    }

    /************************************ WATER ******************************************/
    @RequestMapping(value = "/api/day/water", method = RequestMethod.GET)
    public Iterable<Category> getWaterInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.WATER);
    }

    @RequestMapping(value = "/api/day/water", method = RequestMethod.POST)
    public int putWaterInDayRepository(@RequestBody Water water, @RequestParam(name = "date", required = false) String date) {
        return this.addToDayRepo(water, date);
    }

    @RequestMapping(value = "/api/day/water/points", method = RequestMethod.GET)
    public int getDayWaterPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.WATER);
    }

    /************************************ SNACK ******************************************/
    @RequestMapping(value = "/api/day/snack", method = RequestMethod.GET)
    public Iterable<Category> getSnackInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.SNACK);
    }

    @RequestMapping(value = "/api/day/snack", method = RequestMethod.POST)
    public void putSnackInDayRepository(@RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(snack, date);
    }
    @RequestMapping(value = "/api/day/snack/points", method = RequestMethod.GET)
    public int getDaySnackPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.SNACK);
    }

    /************************************ NUTS ******************************************/
    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.GET)
    public Iterable<Category> getNutsInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.NUTS);
    }

    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.POST)
    public void putNutsInDayRepository(@RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(nuts, date);
    }
    @RequestMapping(value = "/api/day/nuts/points", method = RequestMethod.GET)
    public int getDayNutsPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.NUTS);
    }

    /************************************ MOVEMENT ******************************************/
    @RequestMapping(value = "/api/day/movement", method = RequestMethod.GET)
    public Iterable<Category> getMovementInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.MOVEMENT);
    }

    @RequestMapping(value = "/api/day/movement", method = RequestMethod.POST)
    public void putMovementInDayRepository(@RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(movement, date);
    }

    @RequestMapping(value = "/api/day/movement/points", method = RequestMethod.GET)
    public int getDaymovementPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.MOVEMENT);
    }
    /************************************ FRUIT ******************************************/
    @RequestMapping(value = "/api/day/fruit", method = RequestMethod.GET)
    public Iterable<Category> getFruitInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.FRUIT);
    }

    @RequestMapping(value = "/api/day/fruit", method = RequestMethod.POST)
    public void putFruitInDayRepository(@RequestBody Fruit fruit, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(fruit, date);
    }

    @RequestMapping(value = "/api/day/fruit/points", method = RequestMethod.GET)
    public int getDayFruitPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.FRUIT);
    }
    /************************************ STARCHPRODUCT ******************************************/
    @RequestMapping(value = "/api/day/starchproduct", method = RequestMethod.GET)
    public Iterable<Category> getStarchProductInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.STARCHPRODUCT);
    }

    @RequestMapping(value = "/api/day/starchproduct", method = RequestMethod.POST)
    public void putStarchProductInDayRepository(@RequestBody StarchProduct starchProduct, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(starchProduct, date);
    }

    @RequestMapping(value = "/api/day/starchproduct/points", method = RequestMethod.GET)
    public int getDayStarchProductPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.STARCHPRODUCT);
    }
    /************************************ DAIRYFISHPOULTRY ******************************************/
    @RequestMapping(value = "/api/day/dairyfishpoultry", method = RequestMethod.GET)
    public Iterable<Category> getDairyFishPoultryInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.DAIRYFISHPOULTRY);
    }

    @RequestMapping(value = "/api/day/dairyfishpoultry", method = RequestMethod.POST)
    public void putDairyFishPoultryInDayRepository(@RequestBody DairyFishPoultry dairyFishPoultry, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(dairyFishPoultry, date);
    }

    @RequestMapping(value = "/api/day/dairyfishpoultry/points", method = RequestMethod.GET)
    public int getDayDairyFishPoultryPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.DAIRYFISHPOULTRY);
    }
    /************************************ FATTYFOOD ******************************************/
    @RequestMapping(value = "/api/day/fattyfood", method = RequestMethod.GET)
    public Iterable<Category> getFattyFoodInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.FATTYFOOD);
    }

    @RequestMapping(value = "/api/day/fattyfood", method = RequestMethod.POST)
    public void putFattyFoodInDayRepository(@RequestBody FattyFood fattyFood, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(fattyFood, date);
    }

    @RequestMapping(value = "/api/day/fattyfood/points", method = RequestMethod.GET)
    public int getDayFattyFoodPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.FATTYFOOD);
    }
/****************************************** SIDE FUNCTIONS ************************/
    private int addToDayRepo(Category item, String date) {
        if (item == null) {
            throw new DayRepositoryControllerException("Adding empty item is not allowed");
        } else {
            Day day = this.getDay(date);
            day.add(item);
            this.dayRepository.save(day);
            System.out.println("water has been added to the day:" + day.getDate() + " : " + item.toString());
            return this.getDayWaterPoints(date);
        }

    }
}
