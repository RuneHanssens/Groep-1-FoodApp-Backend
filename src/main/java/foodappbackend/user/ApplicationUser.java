package foodappbackend.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import foodappbackend.model.Day;
import foodappbackend.repositories.DayRepository;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "table_user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String userName;
    private String password;
    private boolean admin = false;
    //@OneToMany(mappedBy = "applicationUser")
    @Lob
    @ElementCollection
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    //@MapKeyTemporal(TemporalType.TIMESTAMP)
//    private ArrayList<Day> days = new ArrayList<Day>();
    private Map<LocalDate, Day> days = new HashMap<>();
//    @Autowired
//    private DayRepository dayRepository;

    public ApplicationUser() {
    }

    public ApplicationUser(/*String name, String lastName, */String userName, String password, /*long age,*/ boolean admin) {

        setUserName(userName);
        setPassword(password);
        setAdmin(admin);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    public ArrayList<Day> getDays() {
//        return days;
//    }
//
//    public void setDays(ArrayList<Day> days) {
//        this.days = days;
//    }

    /*
    public DayRepository getDayRepository() {
        return dayRepository;
    }

    public void setDayRepository(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }
    */

    public Map<LocalDate, Day> getDays() {
        return days;
    }
    public Day getDayIfExists(LocalDate date) {
        if(date == null){
            date = LocalDate.now();
        }
        this.days.putIfAbsent(date, new Day(date));
        return this.days.get(date);
    }

    public void setDays(HashMap<LocalDate, Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        this.days.putIfAbsent(day.getDate(), day);
    }

    public Day getDay(LocalDate date) {
        return this.days.get(date);
    }
}