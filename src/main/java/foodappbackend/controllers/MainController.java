package foodappbackend.controllers;

import com.auth0.jwt.JWT;
import foodappbackend.model.EnumCategory;
import foodappbackend.model.Fruit;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class MainController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String checkStatus() {
        return "200 OK";
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<ApplicationUser> getDayRepository() {
        return this.userRepository.findAll();
    }
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody ApplicationUser applicationUser) throws Exception {
        if(this.userRepository.findByMail(applicationUser.getMail()) != null) throw new Exception("A applicationUser with the submitted email already exists.");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        this.userRepository.save(applicationUser);
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replaceFirst("Bearer ","");
        return JWT.decode(token).getClaim("sub").asString();
//        this.addToDayRepo(EnumCategory.FRUIT, new Fruit(), "");
//        return new RedirectView("/api");
    }
}
