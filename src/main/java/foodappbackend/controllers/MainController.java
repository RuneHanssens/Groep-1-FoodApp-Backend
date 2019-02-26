package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.Day;
import foodappbackend.model.EnumCategory;
import foodappbackend.model.FoodItem;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
import org.apache.tomcat.jni.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public MainController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private String getUserName(String authorizationHeader) {
        String token = authorizationHeader.replaceFirst("Bearer ","");
        return JWT.decode(token).getClaim("sub").asString();
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

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody ApplicationUser applicationUser) throws Exception {
        if(this.userRepository.findByUserName(applicationUser.getUserName()) != null) throw new Exception("A applicationUser with the submitted email already exists.");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        this.userRepository.save(applicationUser);
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<ApplicationUser> getDayRepository() {
        return this.userRepository.findAll();
    }
    @RequestMapping(value = "/user/day", method = RequestMethod.GET)
    public Day getDay(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date){
        Map<LocalDate, Day> days = this.userRepository.findByUserName(this.getUserName(authorizationHeader)).getDays();
        days.putIfAbsent(this.getDate(date), new Day());
        return days.get(this.getDate(date));
    }

    @RequestMapping(value = "/day-api/day/{food_type}", method = RequestMethod.GET)
    public Iterable<FoodItem> getDayVegetableRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }
    @RequestMapping(value = "/day-api/day/{food_type}", method = RequestMethod.POST)
    public int putFoodItemInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody FoodItem foodItem, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.valueOf(food_type.toUpperCase()), foodItem);
        //this.addToDayRepo(EnumCategory.valueOf(food_type.toUpperCase()), (FoodItem)(Class.forName("foodappbackend.model." + food_type.substring(0,1).toUpperCase() + food_type.substring(1))).cast(foodItem), date);
        return this.getDayCategoryPoints(authorizationHeader, date, food_type);
    }
    @RequestMapping(value = "/day-api/day/{food_type}/points", method = RequestMethod.GET)
    public int getDayCategoryPoints(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getPointsCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @RequestMapping(value = "/day-api/day/{food_type}/undo", method = RequestMethod.GET)
    public void removeLastFoodItem(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        this.getDay(authorizationHeader, date).removeLast(EnumCategory.valueOf(food_type.toUpperCase()));
    }
}
