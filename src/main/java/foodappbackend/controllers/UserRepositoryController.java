package foodappbackend.controllers;

import foodappbackend.model.Day;
import foodappbackend.model.EnumCategory;
import foodappbackend.model.User;
import foodappbackend.model.Vegetable;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @RequestMapping(value = "/user-api/user/login-non-admin", method = RequestMethod.POST)
    public String login(@RequestParam(name = "mail", required = true)String mail) {
        String response = "404";
        Iterable<User> users = this.userRepository.findAll();
        for (User u:users){
            if(mail == null || u.getMail().equals(mail)){
                response =  u.getId().toString();
            }
        }
        if(response.equals("404")){
            this.userRepository.save(new User(mail,false));
            response = this.login(mail);
        }
        return response;

    }
    @RequestMapping(value = "/user-api/user/login-admin", method = RequestMethod.POST)
    public String loginAdmin(@RequestParam(name = "mail", required = true)String mail) {
        String response = "404";
        Iterable<User> users = this.userRepository.findAll();
        for (User u:users){
            if(mail == null || u.getMail().equals(mail)){
                response =  u.getId().toString();
            }
        }
        if(response.equals("404")){
            this.userRepository.save(new User(mail,true));
            response = this.login(mail);
        }
        return response;

    }
//    @RequestMapping(value = "/user-api/user/day", method = RequestMethod.POST)
//    public void putDayInUserInUserRepository(@RequestBody Day day, @RequestParam(name = "id", required = true)String id) {
//        User user = null;
//        if(this.userRepository.findById(UUID.fromString(id)).isPresent()){
//            user = this.userRepository.findById(UUID.fromString(id)).get();
//            user.addDay(day);
//        }else {
//            return;
//        }
//    }
//    @RequestMapping(value = "/user-api/user/login", method = RequestMethod.POST)
//    public void login(@RequestBody String mail,String password) {
//        User user = this.userRepository.findById(uuid).get();
//        //this.userRepository.save(D)
//    }

}
