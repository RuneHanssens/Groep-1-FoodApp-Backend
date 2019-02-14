package foodappbackend.controllers;

import foodappbackend.model.Category;
import foodappbackend.model.Day;
import foodappbackend.model.EnumCategory;
import foodappbackend.model.Water;
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
    public void putWaterInDayRepository(@RequestBody Water water, @RequestParam(name = "date", required = false) String date) {
        if (water == null) {
            System.out.println("adding empty water is not allowed: DayRepositoryController : putWaterInDayWaterRepository");
            throw new DayRepositoryControllerException("adding empty water is not allowed: DayRepositoryController : putWaterInDayWaterRepository");
        } else {
            Day day = this.getDay(date);
            day.add(water);
            this.dayRepository.save(day);
            System.out.println("water has been added to the day:" + day.getDate() + " : " + water.toString());
        }
    }

    @RequestMapping(value = "/api/day/water/points", method = RequestMethod.GET)
    public int getDayWaterPoints(@RequestParam(name = "date", required = false) String date) {
        Day day = this.getDay(date);
        return day.getPointsCategory(EnumCategory.WATER);
    }
}
