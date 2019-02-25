package foodappbackend.repositories;

import foodappbackend.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<ApplicationUser,UUID> {
    ApplicationUser findByMail(String mail);
}

