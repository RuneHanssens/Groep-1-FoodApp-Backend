package foodappbackend.controllers;

import foodappbackend.model.*;
import foodappbackend.repositories.DayRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DayRepositoryController {
    private DayRepository dayRepository;

    public DayRepositoryController(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    // FOR DEBUGGING PURPOSES
    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public String APIBasePage() {
        return "<a href=\"/api/status\">/api/status</a><br>" +
                "<a href=\"/api/days\">/api/days</a><br>" +
                "<a href=\"/api/day\">/api/day</a><br>" +
                "&emsp;<a href=\"/api/day/points\">/points</a><br>" +
                "&emsp;<a href=\"/api/day/vegetable\">/vegetable</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/vegetable/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/vegetable/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/water\">/water</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/water/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/water/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/snack\">/snack</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/snack/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/snack/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/nuts\">/nuts</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/nuts/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/nuts/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/movement\">/movement</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/movement/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/movement/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/fruit\">/fruit</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/fruit/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/fruit/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/starchproduct\">/starchproduct</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/starchproduct/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/starchproduct/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/fattyfood\">/fattyfood</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/fattyfood/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/fattyfood/undo\">/undo</a><br>" +
                "&emsp;<a href=\"/api/day/dairyfishpoultry\">/dairyfishpoultry</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/dairyfishpoultry/points\">/points</a><br>" +
                "&emsp;&emsp;<a href=\"/api/day/dairyfishpoultry/undo\">/undo</a><br>" +
                "<br><a href=\"/api/day/fruit/add\">ADD A FRUIT, TESTING PURPOSES ONLY</a>";
    }

    // TODO REMOVE THIS METHOD WHEN TESTING IS NO LONGER REQUIRED
    @RequestMapping(value = "/api/day/fruit/add", method = RequestMethod.GET)
    public RedirectView addTestFruit() {
        this.addToDayRepo(EnumCategory.FRUIT, new Fruit(), "");
        return new RedirectView("/api");
    }

    /************************************ STATUS ************************************/
    @RequestMapping(value = "/api/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }

    /************************************ DAYS **************************************/
    @RequestMapping(value = "/api/days", method = RequestMethod.GET)
    public Iterable<Day> getDayRepository() {
        return this.dayRepository.findAll();
    }

    /************************************ DAY **************************************/
    @RequestMapping(value = "/api/day", method = RequestMethod.GET)
    public Day getDay(@RequestParam(name = "date", required = false) String date) {

        LocalDate localDate = getDate(date);

        if (dayRepository.findById(localDate).isPresent()) {
            return dayRepository.findById(localDate).get();
        }

        return dayRepository.save(new Day(localDate));
    }


    @RequestMapping(value = "/api/day/points", method = RequestMethod.GET)
    public Map<String, Map<String, String>> getDayPoints(@RequestParam(name = "date", required = false) String date) {
        Map<String, Map<String, String>> map = new HashMap<>();
        Day day = this.getDay(date);
        for (EnumCategory c : EnumCategory.values()) {
            map.put(c.toString(), new HashMap<String, String>(){{
                put("Points",String.valueOf(day.getPointsCategory(c)));
                put("OverMin", String.valueOf(day.getCategory(c).getOverMin()));
                put("OverMax", String.valueOf(day.getCategory(c).getOverMax()));
            }});
        }
        return map;
    }

    /************************************ VEGETABLES **************************************/
    @RequestMapping(value = "/api/day/vegetable", method = RequestMethod.GET)
    public Iterable<FoodItem> getDayVegetableRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.VEGETABLE);
    }
    @RequestMapping(value = "/api/day/vegetable", method = RequestMethod.POST)
    public int putVegetableInDayRepository(@RequestBody Vegetable vegetable, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.VEGETABLE, vegetable, date);
        return this.getDayVegetablePoints(date);
    }
    @RequestMapping(value = "/api/day/vegetable/points", method = RequestMethod.GET)
    public int getDayVegetablePoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.VEGETABLE);
    }

    @RequestMapping(value = "/api/day/vegetable/undo", method = RequestMethod.GET)
    public void removeLastVegetable(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.VEGETABLE, date);
    }

    /************************************ WATER ******************************************/
    @RequestMapping(value = "/api/day/water", method = RequestMethod.GET)
    public Iterable<FoodItem> getWaterInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.WATER);
    }

    @RequestMapping(value = "/api/day/water", method = RequestMethod.POST)
    public int putWaterInDayRepository(@RequestBody Water water, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.WATER, water, date);
        return this.getDayWaterPoints(date);
    }

    @RequestMapping(value = "/api/day/water/points", method = RequestMethod.GET)
    public int getDayWaterPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.WATER);
    }

    @RequestMapping(value = "/api/day/water/undo", method = RequestMethod.GET)
    public void removeLastWater(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.WATER, date);
    }

    /************************************ SNACK ******************************************/
    @RequestMapping(value = "/api/day/snack", method = RequestMethod.GET)
    public Iterable<FoodItem> getSnackInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.SNACK);
    }

    @RequestMapping(value = "/api/day/snack", method = RequestMethod.POST)
    public int putSnackInDayRepository(@RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.SNACK, snack, date);
        return this.getDaySnackPoints(date);
    }

    @RequestMapping(value = "/api/day/snack/points", method = RequestMethod.GET)
    public int getDaySnackPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.SNACK);
    }

    @RequestMapping(value = "/api/day/snack/undo", method = RequestMethod.GET)
    public void removeLastSnack(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.SNACK, date);
    }

    /************************************ NUTS ******************************************/
    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.GET)
    public Iterable<FoodItem> getNutsInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.NUTS);
    }

    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.POST)
    public int putNutsInDayRepository(@RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.NUTS, nuts, date);
        return this.getDayNutsPoints(date);
    }
    @RequestMapping(value = "/api/day/nuts/points", method = RequestMethod.GET)
    public int getDayNutsPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.NUTS);
    }

    @RequestMapping(value = "/api/day/nuts/undo", method = RequestMethod.GET)
    public void removeLastNuts(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.NUTS, date);
    }

    /************************************ MOVEMENT ******************************************/
    @RequestMapping(value = "/api/day/movement", method = RequestMethod.GET)
    public Iterable<FoodItem> getMovementInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.MOVEMENT);
    }

    @RequestMapping(value = "/api/day/movement", method = RequestMethod.POST)
    public int putMovementInDayRepository(@RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.MOVEMENT, movement, date);
        return this.getDayMovementPoints(date);
    }

    @RequestMapping(value = "/api/day/movement/points", method = RequestMethod.GET)
    public int getDayMovementPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.MOVEMENT);
    }

    @RequestMapping(value = "/api/day/movement/undo", method = RequestMethod.GET)
    public void removeLastMovement(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.MOVEMENT, date);
    }

    /************************************ FRUIT ******************************************/
    @RequestMapping(value = "/api/day/fruit", method = RequestMethod.GET)
    public Iterable<FoodItem> getFruitInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.FRUIT);
    }

    @RequestMapping(value = "/api/day/fruit", method = RequestMethod.POST)
    public int putFruitInDayRepository(@RequestBody Fruit fruit, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.FRUIT, fruit, date);
        return this.getDayFruitPoints(date);
    }

    @RequestMapping(value = "/api/day/fruit/points", method = RequestMethod.GET)
    public int getDayFruitPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.FRUIT);
    }

    @RequestMapping(value = "/api/day/fruit/undo", method = RequestMethod.GET)
    public void removeLastFruit(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.FRUIT, date);
    }

    /************************************ STARCHPRODUCT ******************************************/
    @RequestMapping(value = "/api/day/starchproduct", method = RequestMethod.GET)
    public Iterable<FoodItem> getStarchProductInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.STARCHPRODUCT);
    }

    @RequestMapping(value = "/api/day/starchproduct", method = RequestMethod.POST)
    public int putStarchProductInDayRepository(@RequestBody StarchProduct starchProduct, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.STARCHPRODUCT, starchProduct, date);
        return this.getDayStarchProductPoints(date);
    }

    @RequestMapping(value = "/api/day/starchproduct/points", method = RequestMethod.GET)
    public int getDayStarchProductPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.STARCHPRODUCT);
    }

    @RequestMapping(value = "/api/day/starchproduct/undo", method = RequestMethod.GET)
    public void removeLastStarchProduct(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.STARCHPRODUCT, date);
    }

    /************************************ DAIRYFISHPOULTRY ******************************************/
    @RequestMapping(value = "/api/day/dairyfishpoultry", method = RequestMethod.GET)
    public Iterable<FoodItem> getDairyFishPoultryInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.DAIRYFISHPOULTRY);
    }

    @RequestMapping(value = "/api/day/dairyfishpoultry", method = RequestMethod.POST)
    public int putDairyFishPoultryInDayRepository(@RequestBody DairyFishPoultry dairyFishPoultry, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.DAIRYFISHPOULTRY, dairyFishPoultry, date);
        return this.getDayDairyFishPoultryPoints(date);
    }

    @RequestMapping(value = "/api/day/dairyfishpoultry/points", method = RequestMethod.GET)
    public int getDayDairyFishPoultryPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.DAIRYFISHPOULTRY);
    }

    @RequestMapping(value = "/api/day/dairyfishpoultry/undo", method = RequestMethod.GET)
    public void removeLastDairyFishPoultry(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.DAIRYFISHPOULTRY, date);
    }

    /************************************ FATTYFOOD ******************************************/
    @RequestMapping(value = "/api/day/fattyfood", method = RequestMethod.GET)
    public Iterable<FoodItem> getFattyFoodInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.FATTYFOOD);
    }

    @RequestMapping(value = "/api/day/fattyfood", method = RequestMethod.POST)
    public int putFattyFoodInDayRepository(@RequestBody FattyFood fattyFood, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(EnumCategory.FATTYFOOD, fattyFood, date);
        return this.getDayFattyFoodPoints(date);
    }

    @RequestMapping(value = "/api/day/fattyfood/points", method = RequestMethod.GET)
    public int getDayFattyFoodPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.FATTYFOOD);
    }

    @RequestMapping(value = "/api/day/fattyfood/undo", method = RequestMethod.GET)
    public void removeLastFattyFood(@RequestParam(name = "date", required = false) String date) {
        this.removeFromDayRepo(EnumCategory.FATTYFOOD, date);
    }

    // Overview functions

    @RequestMapping(value = "/api/overview", method = RequestMethod.GET)
    public List<Day> getOverview(@RequestParam(name = "date", required = false) String date) {
        return getWeek(date);
    }
/****************************************** SIDE FUNCTIONS ************************/
    private void addToDayRepo(EnumCategory category, FoodItem item, String date) {
        if (item == null) {
            throw new DayRepositoryControllerException("Adding empty item is not allowed");
        } else {
            Day day = this.getDay(date);
            day.add(category, item);
            this.dayRepository.save(day);
            // System.out.println("water has been added to the day:" + day.getDate() + " : " + item.toString());
            // return this.getDayWaterPoints(date);
        }
    }

    /**
     * Removes the last item added to a category from a specified day.
     * @param category The Category to remove the last item from.
     * @param date A String in the yyyy-MM-dd format that defines which date the item will be removed from.
     */
    private void removeFromDayRepo(EnumCategory category, String date) {
        Day day = this.getDay(date);
        day.removeLast(category);
        this.dayRepository.save(day);
    }

    /**
     * Returns an object that holds all the info of the last 5 days for inspection.
     * @param date The yyyy-MM-dd form of the LAST day to be retrieved.
     * @return A List of five Day objects.
     */
    private List<Day> getWeek(String date) {
        ArrayList<Day> week = new ArrayList<>();
        LocalDate localDate = getDate(date);
        for(int i = 0; i<5; i++) {
            if (dayRepository.findById(localDate).isPresent())
                week.add(dayRepository.findById(localDate).get());
            else
                week.add(dayRepository.save(new Day((localDate))));
            localDate = localDate.minusDays(1);
        }

        return week;
    }

    /**
     * Helper method to turn a date string into a usable localdate object, if the date is incomprehensible today's date will be used.
     * @param date The String to convert to a LocalDate object, in the yyyy-MM-dd format.
     * @return A new LocalDate object.
     */
    private LocalDate getDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy]");
            return LocalDate.parse(date, formatter);
        }
    }
}
