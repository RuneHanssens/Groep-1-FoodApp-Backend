package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.*;
//import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
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
        if(this.userRepository.findByUsername(applicationUser.getUsername()) != null) throw new Exception("A applicationUser with the submitted email already exists.");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        this.userRepository.save(applicationUser);
        System.out.println("MainController - sign up - aantal users: " + this.userRepository.count());
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<ApplicationUser> getDayRepository() {
        return this.userRepository.findAll();
    }
    @RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
    public void changeUserPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String password) throws Exception{
        ApplicationUser applicationUser = this.userRepository.findByUsername(this.getUser(authorizationHeader).getUsername());
        if(applicationUser == null) throw new Exception("Invalid Credentials for a password change");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(password));
        this.userRepository.save(applicationUser);
        System.out.println("password of user: "+applicationUser.getUsername()+" has changed.");
    }
    @RequestMapping(value = "/user/day", method = RequestMethod.GET)
    public Day getDay(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date){
        try {
            return this.getUser(authorizationHeader).getDayIfExists(this.getDate(date)/*localDate*/);
        } catch(NullPointerException e) {
            System.out.println(e.getStackTrace());
            System.out.println(this.getUser(authorizationHeader + "\n" + date));
            return null;
        }
    }

    private Day getDay(String username, LocalDate date) {
        return this.userRepository.findByUsername(username).getDayIfExists(date);
    }

    private ApplicationUser getUser(String authorizationHeader) {
        return this.userRepository.findByUsername(this.getUserName(authorizationHeader));
    }

    @RequestMapping(value = "/user/day/points", method = RequestMethod.GET)
    public Map<String, Map<String, String>> getDayPoints(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date) {
        Map<String, Map<String, String>> map = new HashMap<>();
        Day day = this.getDay(authorizationHeader, date);
        for (EnumCategory c : EnumCategory.values()) {
            map.put(c.toString(), new HashMap<String, String>(){{
                put("Points",String.valueOf(day.getPointsCategory(c)));
                put("OverMin", String.valueOf(day.getCategory(c).getOverMin()));
                put("OverMax", String.valueOf(day.getCategory(c).getOverMax()));
            }});
        }
        return map;
    }

    @RequestMapping(value = "/user/dayrange", method = RequestMethod.GET)
    public Map<String, Map<String, String>> getTimePeriod(@RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate, @RequestParam(name = "category") String category, @RequestParam(value = "username") String username) {
        HashMap<String, Map<String, String>> res = new HashMap<>();
        for(LocalDate current = this.getDate(startDate); !current.isAfter(this.getDate(endDate)); current.plusDays(1)) {
            Day day = this.getDay(username, current);
            res.put(current.toString(), new HashMap<String, String>(){{
                put("Points", String.valueOf(day.getPointsCategory(category)));
                put("OverMin", String.valueOf(day.getCategory(category).getOverMin()));
                put("OverMax", String.valueOf(day.getCategory(category).getOverMax()));
            }});
        }
        return null;
    }

    @RequestMapping(value = "/user/day/{food_type}", method = RequestMethod.GET)
    public Iterable<FoodItem> getDayCategoryRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @RequestMapping(value = "/user/day/item/{food_type}", method = RequestMethod.GET)
    public FoodItem test(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getCategory(EnumCategory.valueOf(food_type.toUpperCase())).get(0);
    }

    @RequestMapping(value = "/user/day/{food_type}/points", method = RequestMethod.GET)
    public int getDayCategoryPoints(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getPointsCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @RequestMapping(value = "/user/day/{food_type}/undo", method = RequestMethod.GET)
    public int removeLastFoodItem(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).removeLast(EnumCategory.valueOf(food_type.toUpperCase()));
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, food_type);
    }

    /***************************************** POST ***************************************/
    @RequestMapping(value = "/user/day/dairyfishpoultry", method = RequestMethod.POST)
    public int putDairyFishPoultryInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody DairyFishPoultry dairyFishPoultry, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.DAIRYFISHPOULTRY, dairyFishPoultry);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "dairyfishpoultry");
    }
    @RequestMapping(value = "/user/day/fattyfood", method = RequestMethod.POST)
    public int putFattyFoodInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody FattyFood fattyFood, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.FATTYFOOD, fattyFood);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "fattyfood");
    }
    @RequestMapping(value = "/user/day/fruit", method = RequestMethod.POST)
    public int putFruitInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Fruit fruit, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.FRUIT, fruit);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "fruit");
    }
    @RequestMapping(value = "/user/day/movement", method = RequestMethod.POST)
    public int putMovementInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.MOVEMENT, movement);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "movement");
    }
    @RequestMapping(value = "/user/day/nuts", method = RequestMethod.POST)
    public int putNutsInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.NUTS, nuts);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "nuts");
    }
    @RequestMapping(value = "/user/day/snack", method = RequestMethod.POST)
    public int putSnackInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.SNACK, snack);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "snack");
    }
    @RequestMapping(value = "/user/day/starchproduct", method = RequestMethod.POST)
    public int putStarchProductInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StarchProduct starchProduct, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.STARCHPRODUCT, starchProduct);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "starchproduct");
    }
    @RequestMapping(value = "/user/day/vegetable", method = RequestMethod.POST)
    public int putVegetableInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Vegetable vegetable, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.VEGETABLE, vegetable);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "vegetable");
    }
    @RequestMapping(value = "/user/day/water", method = RequestMethod.POST)
    public int putWaterInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Water water, @RequestParam(name = "date", required = false) String date) throws ClassNotFoundException {
        //LocalDate localDate = this.getDate(date);
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.WATER, water);
        System.out.println("saving user for water");
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "water");
    }

}
