package foodappbackend.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "table_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
//    @Id
    private String mail;
//    private String name;
//    private String lastName;
    private String password;
//    private long age;
    private boolean admin = false;

    @Lob
    @ElementCollection
    private Map<LocalDate,Day> days;

    public User() {

    }

    public User(/*String name, String lastName, */String mail, String password, /*long age,*/ boolean admin) {
//        setName(name);
//        setLastName(lastName);
        setMail(mail);
        setPassword(password);
//        setAge(age);
//        setAdmin(admin);
        setDays(new HashMap<>());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


//    public boolean isAdmin() {
//        return admin;
//    }
//
//    public void setAdmin(boolean admin) {
//        this.admin = admin;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
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

    public Map<LocalDate, Day> getDays() {
        return days;
    }

    public void setDays(Map<LocalDate, Day> days) {
        this.days = days;
    }
    public void addDay(Day day){
        this.days.put(day.getDate(), day);
    }
    public Day getDay(LocalDate date){
        return this.days.get(date);
    }

//    public long getAge() {
//        return age;
//    }
//
//    public void setAge(long age) {
//        this.age = age;
//    }
//    private String HashFunction(String string){
//        String Hashed;
//
//        HashCohashCode();
////        SecureRandom secureRandom = new SecureRandom();
////        byte[] salt = new byte[16];
////        secureRandom.nextBytes(salt);
//
//        return Hashed;
//    }
}
