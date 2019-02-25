package foodappbackend.controllers;

import foodappbackend.model.User;
import foodappbackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@RestController
public class UserRepositoryController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/user-api/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }

    @RequestMapping(value = "/user-api/users", method = RequestMethod.GET)
    public Iterable<User> getDayRepository() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/user-api/user/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody User user) {
        Iterable<User> users = this.userRepository.findAll();
        for (User u : users) {
            if (u.getMail().equals(user.getMail())) {
                return;
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @RequestMapping(value = "/user-api/user/sign-in", method = RequestMethod.POST)
    public String signIn(@RequestBody String mail, String password) {
//        String response = "404";
//        Iterable<User> users = this.userRepository.findAll();
//        for (User u : users) {
//            if (mail == null || u.getMail().equals(mail)) {
//                response = u.getId().toString();
//            }
//        }
//        return response;
        return "not yet implemented.";
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
