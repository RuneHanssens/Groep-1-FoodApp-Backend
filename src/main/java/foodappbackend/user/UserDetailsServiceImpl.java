package foodappbackend.user;


import foodappbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.userRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUserName(userName);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(userName);
        }
        System.out.println("token user loaded");
        return new User(applicationUser.getUserName(), applicationUser.getPassword(), emptyList());
    }
}
