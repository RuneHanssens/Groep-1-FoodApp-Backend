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

    //@JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Map<String, Day> days;

    public ApplicationUser() { }

    public ApplicationUser(String username, String password, boolean admin) {
        setUsername(username);
        setPassword(password);
        setAdmin(admin);
        //this.days = new HashMap<>();
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

    public Map<String, Day> getDays() {
        return days;
    }

    public Day getDayIfExists(LocalDate date) {
        if(date == null){
            date = LocalDate.now();
        }
        if (!this.days.containsKey(date + ":" + this.getUsername())){
            this.addDay( new Day(date, this));
        }
        return this.days.get(date + ":" + this.getUsername());
    }

    /**
     *
     * @param date
     * @return
     */
    public Day getDayIfExists(String date) {
        this.days.putIfAbsent(date + ":" + this.getUsername(), new Day(date, this));
        return this.days.get(date + ":" + this.getUsername());
    }

    public void setDays(HashMap<String, Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        this.days.putIfAbsent(day.getDate() + ":" + this.getUsername(), day);
    }

    public Day getDay(LocalDate date) {
        return this.days.get(date);
    }
}