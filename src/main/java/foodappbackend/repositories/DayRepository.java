package foodappbackend.repositories;


import foodappbackend.model.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface  DayRepository extends CrudRepository<Day,LocalDate>{};

//import org.apache.tomcat.jni.Local;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class DayRepository {
//    private HashMap<LocalDate, Day> days;
//    public  DayRepository(){
//        days = new HashMap<LocalDate, Day>();
//    }
//    public void addDay(Day d){
//        days.put(d.getDate(), d);
//    }
//
//    public Day getDay(LocalDate date) {
//        return days.get(date);
//    }
//
//}
