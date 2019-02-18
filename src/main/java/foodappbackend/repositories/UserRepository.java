package foodappbackend.repositories;

import foodappbackend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User,UUID> {};

