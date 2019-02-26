package foodappbackend.user;

import foodappbackend.controllers.DayRepositoryController;
import foodappbackend.model.Day;
import foodappbackend.repositories.DayRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private String mail;
    private String password;
    private boolean admin = false;
    //@OneToMany(cascade = CascadeType.ALL)
    @Lob
//    private ArrayList<Day> days = new ArrayList<Day>();
//    private HashMap<LocalDate, Day> days;
    private DayRepository dayRepository;

    public ApplicationUser() {

    }

    public ApplicationUser(/*String name, String lastName, */String mail, String password, /*long age,*/ boolean admin) {

        setMail(mail);
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public DayRepository getDayRepository() {
        return dayRepository;
    }

    public void setDayRepository(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

//    public Map<LocalDate, Day> getDays() {
//        return days;
//    }
//
//    public void setDays(HashMap<LocalDate, Day> days) {
//        this.days = days;
//    }
}
//    public void addDay(Day day){
//        this.days.put(day.getDate(), day);
//    }
//    public Day getDay(LocalDate date){
//        return this.days.get(date);
//    }