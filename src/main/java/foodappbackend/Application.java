package foodappbackend;
import foodappbackend.model.Day;
import foodappbackend.model.User;
import foodappbackend.model.Vegetable;
import foodappbackend.model.Water;
import javax.sql.DataSource;
import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner DayCommandLineRunner(DayRepository dayRepository){
        return (args) -> {
//            Day day = new Day(LocalDate.of(1999,1,1));
//            day.add(new Vegetable(Vegetable.Type.LESS));
//            dayRepository.save(day);
//
//            Day today = new Day(LocalDate.now());
//            today.add(new Vegetable(Vegetable.Type.OUTDOOR));
//            today.add(new Water(1));
//            dayRepository.save(today);
        };
    }
    @Bean
    public CommandLineRunner UserCommandLineRunner(UserRepository userRepository){
        return (args) -> {
//            User user = new User();
//            userRepository.save(user);
            User user2 = new User("Reinout.Vanhauwaert@student.ucll.be",true);
            userRepository.save(user2);
            User user3 = new User("Gebruiker@Gebruiker.com",false);
            userRepository.save(user3);
        };
    }
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//    @Bean
//    public DataSource dataSource() {
//
//        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase db = builder
//                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
//                .addScript("db/sql/create-db.sql")
//                .addScript("db/sql/insert-data.sql")
//                .build();
//        return db;
//    }


}