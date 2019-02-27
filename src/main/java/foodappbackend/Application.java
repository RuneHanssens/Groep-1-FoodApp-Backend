package foodappbackend;
import foodappbackend.model.*;

//import foodappbackend.repositories.DayRepository;
import foodappbackend.repositories.UserRepository;
import foodappbackend.user.ApplicationUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//    @Bean
//    public CommandLineRunner DayCommandLineRunner(DayRepository dayRepository){
//        return (args) -> {
//            Day day = new Day(LocalDate.of(2019,2,22));
//            day.add(EnumCategory.VEGETABLE,new Vegetable(true));
//            day.add(EnumCategory.WATER,new Water(5));
//            day.add(EnumCategory.MOVEMENT,new Movement(Movement.Type.ZWEMMEN,60));
//            day.add(EnumCategory.DAIRYFISHPOULTRY,new DairyFishPoultry(DairyFishPoultry.Type.EGG));
//            day.add(EnumCategory.DAIRYFISHPOULTRY,new DairyFishPoultry(DairyFishPoultry.Type.CHICKEN));
//            dayRepository.save(day);
//
//            Day day2 = new Day(LocalDate.of(2019,2,20));
//            day.add(EnumCategory.VEGETABLE,new Vegetable(false));
//            day.add(EnumCategory.WATER,new Water(2));
//            day.add(EnumCategory.MOVEMENT,new Movement(Movement.Type.WANDELEN,5));
//            day.add(EnumCategory.DAIRYFISHPOULTRY,new DairyFishPoultry(DairyFishPoultry.Type.FISH));
//            day.add(EnumCategory.NUTS,new Nuts(true));
//            dayRepository.save(day2);
//
//            Day today = new Day(LocalDate.now());
//            day.add(EnumCategory.SNACK,new Snack());
//            day.add(EnumCategory.WATER,new Water(3));
//            day.add(EnumCategory.VEGETABLE,new Vegetable(false));
//            day.add(EnumCategory.MOVEMENT,new Movement(Movement.Type.FIETSEN,30));
//            day.add(EnumCategory.DAIRYFISHPOULTRY,new DairyFishPoultry(DairyFishPoultry.Type.CHICKEN));
//            dayRepository.save(today);
//        };
//    }
//    @Bean
//    public CommandLineRunner UserCommandLineRunner(UserRepository userRepository){
//        return (args) -> {
////            ApplicationUser user = new ApplicationUser();
////            userRepository.save(user);
////            ApplicationUser applicationUser = new ApplicationUser("Rune","t",true);
////            userRepository.save(applicationUser);
////            ApplicationUser applicationUser2 = new ApplicationUser("Reinout.Vanhauwaert@student.ucll.be","t",true);
////            userRepository.save(applicationUser2);
////            ApplicationUser applicationUser3 = new ApplicationUser("Gebruiker@Gebruiker.com","t",false);
////            userRepository.save(applicationUser3);
//        };
//    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
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