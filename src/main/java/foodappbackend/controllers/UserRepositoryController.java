package foodappbackend.controllers;

import foodappbackend.model.Day;
import foodappbackend.model.EnumCategory;
import foodappbackend.model.User;
import foodappbackend.model.Vegetable;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserRepositoryController {
    private UserRepository userRepository;

    public UserRepositoryController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @RequestMapping(value = "/user-api/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }
    @RequestMapping(value = "/user-api/users", method = RequestMethod.GET)
    public Iterable<User> getDayRepository() {
        return this.userRepository.findAll();
    }
    @RequestMapping(value = "/user-api/user/day", method = RequestMethod.POST)
    public void putDayInUserInUserRepository(@RequestBody Day day, @RequestParam(name = "id", required = true)String id) {

        User user = this.userRepository.findById(UUID.fromString(id)).get();
        //this.userRepository.save(D)
    }
//    @RequestMapping(value = "/user-api/user/login", method = RequestMethod.POST)
//    public void login(@RequestBody String mail,String password) {
//        User user = this.userRepository.findById(uuid).get();
//        //this.userRepository.save(D)
//    }

}
