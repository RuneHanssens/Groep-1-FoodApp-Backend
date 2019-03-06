package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.*;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping(value = "/status")
    public String checkStatus() {
        return "200 OK";
    }

    @PostMapping(value = "/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) throws Exception {
        if(applicationUser.getUsername() == null) throw new Exception("No username provided");
        if(applicationUser.getPassword() == null) throw new Exception("No password provided");
        if(this.userRepository.findByUsername(applicationUser.getUsername()) != null) throw new Exception("An applicationUser with the submitted email already exists.");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        this.userRepository.save(applicationUser);
        System.out.println("MainController - sign up - aantal users: " + this.userRepository.count());
    }

    @GetMapping(value = "/users")
    public Iterable<ApplicationUser> getDayRepository() {
        return this.userRepository.findAll();
    }

    @GetMapping(value = "/user/change-password")
    public void changeUserPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String password) throws Exception{
        // This is for a bad auth token
        if(this.getUser(authorizationHeader) == null) throw new Exception("No user specified");
        ApplicationUser applicationUser = this.userRepository.findByUsername(this.getUser(authorizationHeader).getUsername());
        // This is for when the user in the token does not exist in the db
        if(applicationUser == null) throw new Exception("Invalid Credentials for a password change");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(password));
        this.userRepository.save(applicationUser);
        System.out.println("password of user: "+applicationUser.getUsername()+" has changed.");
    }

    public Day getDay(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date){
        try {
            return this.getUser(authorizationHeader).getDayIfExists(this.getDate(date));
        } catch(NullPointerException e) {
            return null;
        }
    }

    @GetMapping(value = "/site/day")
    public Map<String,Map<String,String>> test(@RequestParam("username") String username, @RequestParam("date") String date) {
        Map<String, Map<String, String>> map = new HashMap<>();
        Day day = this.userRepository.findByUsername(username).getDayIfExists(this.getDate(date));
        for (EnumCategory c : EnumCategory.values()) {
            map.put(c.toString(), new HashMap<String, String>(){{
                put("Points",String.valueOf(day.getPointsCategory(c)));
                put("OverMin", String.valueOf(day.getCategory(c).getOverMin()));
                put("OverMax", String.valueOf(day.getCategory(c).getOverMax()));
            }});
        }
        return map;
    }

    private Day getDay(String username, LocalDate date) {
        if (this.userRepository.findByUsername(username) == null) throw new IllegalArgumentException("User with username " + username + " was not found.");
        return this.userRepository.findByUsername(username).getDayIfExists(date);
    }

    @PostMapping(value = "/user")
    public String getUserFromToken(@RequestHeader("Authorization") String token) {
        try {
            return this.userRepository.findByUsername(this.getUserName(token)).getUsername();
        } catch(NullPointerException | IllegalArgumentException e) {
            return "User not found.";
        }
    }

    private ApplicationUser getUser(String authorizationHeader) {

        return this.userRepository.findByUsername(this.getUserName(authorizationHeader));
    }

    @GetMapping(value = "/user/day/points")
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

    @GetMapping(value = "/user/dayrange")
    public Map<String, Map<String, String>> getTimePeriod(@RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate, @RequestParam(name = "category") String category, @RequestParam(value = "username") String username) {
        Map<String, Map<String, String>> res = new HashMap<>();
        for(LocalDate current = this.getDate(startDate); !current.isAfter(this.getDate(endDate)); current = current.plusDays(1)) {
            Day day = this.getDay(username, current);
            res.put(current.toString(), new HashMap<String, String>(){{
                put("Points", String.valueOf(day.getPointsCategory(category)));
                put("OverMin", String.valueOf(day.getCategory(category).getOverMin()));
                put("OverMax", String.valueOf(day.getCategory(category).getOverMax()));
            }});
        }
        return res;
    }

    @GetMapping(value = "/user/day/{food_type}")
    public Iterable<FoodItem> getDayCategoryRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @GetMapping(value = "/site/user/day/{food_type}")
    public List<String> getDayCategoryRepositoryByUsername(@RequestParam("username") String username, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(username, this.getDate(date));
        List<String> res = new ArrayList<>();
        for(FoodItem f : day.getCategory(EnumCategory.valueOf(food_type.toUpperCase()))) {
            res.add(f.getReadableString());
        }
        return res;
    }

    @GetMapping(value = "/user/day/{food_type}/points")
    public int getDayCategoryPoints(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        Day day = this.getDay(authorizationHeader, date);
        return day.getPointsCategory(EnumCategory.valueOf(food_type.toUpperCase()));
    }

    @GetMapping(value = "/user/day/{food_type}/undo")
    public int removeLastFoodItem(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name = "date", required = false) String date, @PathVariable String food_type) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).removeLast(EnumCategory.valueOf(food_type.toUpperCase()));
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, food_type);
    }

    @GetMapping(value = "/user/{food_type}")
    public Map<String, Integer> getExtrema(@PathVariable String food_type) {
        return new HashMap<String, Integer>(){{
            this.put("min: ", EnumCategory.valueOf(food_type.toUpperCase()).getMin());
            this.put("max: ", EnumCategory.valueOf(food_type.toUpperCase()).getMax());
        }};
    }

    /***************************************** POST ***************************************/
    @PostMapping(value = "/user/day/dairyfishpoultry")
    public int putDairyFishPoultryInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody DairyFishPoultry dairyFishPoultry, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.DAIRYFISHPOULTRY, dairyFishPoultry);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "dairyfishpoultry");
    }

    @PostMapping(value = "/user/day/fattyfood")
    public int putFattyFoodInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody FattyFood fattyFood, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.FATTYFOOD, fattyFood);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "fattyfood");
    }

    @PostMapping(value = "/user/day/fruit")
    public int putFruitInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Fruit fruit, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.FRUIT, fruit);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "fruit");
    }

    @PostMapping(value = "/user/day/movement")
    public int putMovementInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Movement movement, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.MOVEMENT, movement);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "movement");
    }

    @PostMapping(value = "/user/day/nuts")
    public int putNutsInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Nuts nuts, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.NUTS, nuts);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "nuts");
    }

    @PostMapping(value = "/user/day/snack")
    public int putSnackInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Snack snack, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.SNACK, snack);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "snack");
    }

    @PostMapping(value = "/user/day/starchproduct")
    public int putStarchProductInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StarchProduct starchProduct, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.STARCHPRODUCT, starchProduct);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "starchproduct");
    }

    @PostMapping(value = "/user/day/vegetable")
    public int putVegetableInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Vegetable vegetable, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.VEGETABLE, vegetable);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "vegetable");
    }

    @PostMapping(value = "/user/day/water")
    public int putWaterInDayRepository(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Water water, @RequestParam(name = "date", required = false) String date) {
        ApplicationUser user = this.getUser(authorizationHeader);
        user.getDayIfExists(this.getDate(date)).add(EnumCategory.WATER, water);
        this.userRepository.save(user);
        return this.getDayCategoryPoints(authorizationHeader, date, "water");
    }
}
