package foodappbackend.user;

import foodappbackend.repositories.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
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
    //@OneToMany(cascade = CascadeType.ALL)
    @Lob
//    private ArrayList<Day> days = new ArrayList<Day>();
//    private HashMap<LocalDate, Day> days;
    @Autowired
    private DayRepository dayRepository;

    public ApplicationUser() {

    }

    public ApplicationUser(/*String name, String lastName, */String userName, String password, /*long age,*/ boolean admin, DayRepository dayRepository) {

        setUserName(userName);
        setPassword(password);
        setAdmin(admin);
        setDayRepository(dayRepository);
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