package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.*;
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
        if (date == null || date.trim().isEmpty() || date.equals("null")) {
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
        //LocalDate localDate = this.getDate(date);
        System.out.println(this.getDate(date));
        System.out.println(this.getUserName(authorizationHeader));
        System.out.println(this.userRepository.findByUserName(this.getUserName(authorizationHeader)).getDayIfExists(this.getDate(date)));
        return this.userRepository.findByUserName(this.getUserName(authorizationHeader)).getDayIfExists(this.getDate(date)/*localDate*/);
//        days.putIfAbsent(this.getDate(date), new Day());
////        return days.get(this.getDate(date));
    }

    @RequestMapping(value = "/user/day/{food_type}", method = RequestMethod.GET)
    public Iterable<FoodItem> getDayVegetableRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        System.out.println(EnumCategory.valueOf(food_type.toUpperCase()));
        return day.getCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }
    @RequestMapping(value = "/user/day/{food_type}/points", method = RequestMethod.GET)
    public int getDayCategoryPoints(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getPointsCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @RequestMapping(value = "/user/day/{food_type}/undo", method = RequestMethod.GET)
    public void removeLastFoodItem(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        this.getDay(authorizationHeader, date).removeLast(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    /***************************************** POST ***************************************/
    @RequestMapping(value = "/user/day/dairyfishpoultry", method = RequestMethod.POST)
    public int putDairyFishPoultryInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody DairyFishPoultry dairyFishPoultry, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.DAIRYFISHPOULTRY, dairyFishPoultry);
        //this.addToDayRepo(EnumCategory.valueOf(food_type.toUpperCase()), (FoodItem)(Class.forName("foodappbackend.model." + food_type.substring(0,1).toUpperCase() + food_type.substring(1))).cast(foodItem), date);
        return this.getDayCategoryPoints(authorizationHeader, date, "dairyfishpoultry");
    }
    @RequestMapping(value = "/user/day/fattyfood", method = RequestMethod.POST)
    public int putFattyFoodInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody FattyFood fattyFood, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.FATTYFOOD, fattyFood);
        return this.getDayCategoryPoints(authorizationHeader, date, "fattyfood");
    }
    @RequestMapping(value = "/user/day/fruit", method = RequestMethod.POST)
    public int putFruitInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Fruit fruit, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.FRUIT, fruit);
        return this.getDayCategoryPoints(authorizationHeader, date, "fruit");
    }
    @RequestMapping(value = "/user/day/movement", method = RequestMethod.POST)
    public int putMovementInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.MOVEMENT, movement);
        return this.getDayCategoryPoints(authorizationHeader, date, "movement");
    }
    @RequestMapping(value = "/user/day/nuts", method = RequestMethod.POST)
    public int putNutsInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.NUTS, nuts);
        return this.getDayCategoryPoints(authorizationHeader, date, "nuts");
    }
    @RequestMapping(value = "/user/day/snack", method = RequestMethod.POST)
    public int putSnackInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.SNACK, snack);
        return this.getDayCategoryPoints(authorizationHeader, date, "snack");
    }
    @RequestMapping(value = "/user/day/starchproduct", method = RequestMethod.POST)
    public int putStarchProductInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StarchProduct starchProduct, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.STARCHPRODUCT, starchProduct);
        return this.getDayCategoryPoints(authorizationHeader, date, "starchproduct");
    }
    @RequestMapping(value = "/user/day/vegetable", method = RequestMethod.POST)
    public int putVegetableInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Vegetable vegetable, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        this.getDay(authorizationHeader, date).add(EnumCategory.VEGETABLE, vegetable);
        return this.getDayCategoryPoints(authorizationHeader, date, "vegetable");
    }
    @RequestMapping(value = "/user/day/water", method = RequestMethod.POST)
    public int putWaterInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Water water, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        //LocalDate localDate = this.getDate(date);
        System.out.println(date/*localDate.toString()*/);
        this.getDay(authorizationHeader, date/*localDate.toString()*/).add(EnumCategory.WATER, water);
        return this.getDayCategoryPoints(authorizationHeader, date/*localDate.toString()*/, "water");
    }

}