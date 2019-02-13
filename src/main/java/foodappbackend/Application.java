package foodappbackend;
import foodappbackend.model.Day;
import foodappbackend.model.Vegetable;
import foodappbackend.repositories.DayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
            Day day = new Day(LocalDate.of(1999,1,1));
            day.add(new Vegetable(false,false));
            dayRepository.save(day);

            Day today = new Day(LocalDate.now());
            today.add(new Vegetable(false,true));
            dayRepository.save(today);
        };
    }

}