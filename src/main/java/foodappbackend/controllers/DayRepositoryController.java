package foodappbackend.controllers;

import foodappbackend.model.*;
import foodappbackend.repositories.DayRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
public class DayRepositoryController {
    private DayRepository dayRepository;

    public DayRepositoryController(DayRepository dayRepository) {
        this.dayRepository = dayRepository;

    }

    @RequestMapping(value = "/api/days", method = RequestMethod.GET)
    public Iterable<Day> getDayRepository() {

        return this.dayRepository.findAll();
    }

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

    @RequestMapping(value = "/api/day/vegetables", method = RequestMethod.GET)
    public int getDayVegetableRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.VEGETABLE);
    }

    @RequestMapping(value = "/api/day/water", method = RequestMethod.GET)
    public Iterable<Category> getWaterInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.WATER);
    }

    @RequestMapping(value = "/api/day/water", method = RequestMethod.POST)
    public int putWaterInDayRepository(@RequestBody Water water, @RequestParam(name = "date", required = false) String date) {
        return this.addToDayRepo(water, date);
    }

    @RequestMapping(value = "/api/day/snack", method = RequestMethod.GET)
    public Iterable<Category> getSnackInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.SNACK);
    }

    @RequestMapping(value = "/api/day/snack", method = RequestMethod.POST)
    public void putSnackInDayRepository(@RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(snack, date);
    }

    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.GET)
    public Iterable<Category> getNutsInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.NUTS);
    }

    @RequestMapping(value = "/api/day/nuts", method = RequestMethod.POST)
    public void putNutsInDayRepository(@RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(nuts, date);
    }

    @RequestMapping(value = "/api/day/movement", method = RequestMethod.GET)
    public Iterable<Category> getMovementInDayRepository(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getCategory(EnumCategory.MOVEMENT);
    }

    @RequestMapping(value = "/api/day/movement", method = RequestMethod.POST)
    public void putMovementInDayRepository(@RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) {
        this.addToDayRepo(movement, date);
    }

    private int addToDayRepo(Category item, String date) {
        if(item == null) {
            throw new DayRepositoryControllerException("Adding empty item is not allowed");
        }
        else {
            Day day = this.getDay(date);
            day.add(item);
            this.dayRepository.save(day);
            System.out.println("water has been added to the day:" + day.getDate() + " : " + item.toString());
            return this.getDayWaterPoints(date);
        }

    }

    @RequestMapping(value = "/api/day/water/points", method = RequestMethod.GET)
    public int getDayWaterPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.WATER);
    }
}
