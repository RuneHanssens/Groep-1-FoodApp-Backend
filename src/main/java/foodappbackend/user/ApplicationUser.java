package foodappbackend.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import foodappbackend.model.Day;
//import foodappbackend.repositories.DayRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
public class ApplicationUser implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "admin")
    private boolean admin = false;

//    @Lob
    //@ElementCollection
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Map<LocalDate, Day> days = new HashMap<>();

    public ApplicationUser() {
    }

    public ApplicationUser(/*String name, String lastName, */String username, String password, /*long age,*/ boolean admin) {
        setUsername(username);
        setPassword(password);
        setAdmin(admin);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<LocalDate, Day> getDays() {
        return days;
    }

    public Day getDayIfExists(LocalDate date) {
        if(date == null){
            date = LocalDate.now();
        }
        if (this.days.containsKey(date)){

        } else {
            this.addDay( new Day(date));
        }
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