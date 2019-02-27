package foodappbackend.repositories;

import foodappbackend.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findByUserName(String userName);
}

