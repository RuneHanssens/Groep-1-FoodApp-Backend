package foodappbackend.repositories;

import foodappbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    User findByMail(String mail);
}

