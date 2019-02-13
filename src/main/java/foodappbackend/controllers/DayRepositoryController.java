package foodappbackend.controllers;

import foodappbackend.model.Day;
import foodappbackend.repositories.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
public class DayRepositoryController {
    private DayRepository dayRepository;

    public DayRepositoryController(DayRepository dayRepository){
        this.dayRepository = dayRepository;
        dayRepository.save(new Day());
    }

    @RequestMapping(value = "/api/day", method = RequestMethod.GET)
    public Day getDayRepository(@RequestParam(name = "date", required = false) String date) {

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

}
