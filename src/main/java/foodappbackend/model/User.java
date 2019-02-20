package foodappbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.SecureRandom;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String lastname;
    private String mail;
    private String password;
    private long age;
    private boolean admin = false;

    public  User(){

    }
    public User(String name,String lastname,String mail,String password,long age,boolean admin){
        setName(name);
        setLastname(lastname);
        setMail(mail);
        setPassword(password);
        setAge(age);
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
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
