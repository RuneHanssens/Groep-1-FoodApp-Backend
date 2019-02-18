package foodappbackend.controllers;

import foodappbackend.model.Day;
import foodappbackend.model.User;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
