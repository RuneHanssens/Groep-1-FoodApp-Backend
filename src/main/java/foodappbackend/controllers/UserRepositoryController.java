package foodappbackend.controllers;

import foodappbackend.user.User;
import foodappbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@RestController
@RequestMapping("/user-api")
public class UserRepositoryController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> getDayRepository() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/user/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody User user) throws Exception {
        if(this.userRepository.findByMail(user.getMail()) != null) throw new Exception("A user with the submitted email already exists.");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @RequestMapping(value = "/user/sign-in", method = RequestMethod.POST)
    public String signIn(@RequestBody String mail, String password) throws UsernameNotFoundException {
        User user = this.userRepository.findByMail(mail);
        if(user == null) throw new UsernameNotFoundException(mail);
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
//    @RequestMapping(value = "/user/day", method = RequestMethod.POST)
//    public void putDayInUserInUserRepository(@RequestBody Day day, @RequestParam(name = "id", required = true)String id) {
//        User user = null;
//        if(this.userRepository.findById(UUID.fromString(id)).isPresent()){
//            user = this.userRepository.findById(UUID.fromString(id)).get();
//            user.addDay(day);
//        }else {
//            return;
//        }
//    }
//    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
//    public void login(@RequestBody String mail,String password) {
//        User user = this.userRepository.findById(uuid).get();
//        //this.userRepository.save(D)
//    }

}
