package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.Day;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        DayRepository dayRepository = this.userRepository.findByUserName(this.getUserName(authorizationHeader)).getDayRepository();
        LocalDate localDate = this.getDate(date);
        if (dayRepository.findById(localDate).isPresent()) {
            return dayRepository.findById(localDate).get();
        }

        return dayRepository.save(new Day(localDate));
    }
}
