package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import foodappbackend.user.ApplicationUser;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "days")
@IdClass(CompositeKey.class)
public class Day {
    @Id
    @Column(name = "localDate")
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate date;
    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ApplicationUser user;
    //@CollectionTable(name="list_of_categories")
    //private List<FoodItem> foodItems = new ArrayList<>();
    @Column(name = "categories")
    @Lob
    @ElementCollection
//    @OneToMany(cascade = CascadeType.ALL)
    private Map<EnumCategory,Category<FoodItem>> categories = new HashMap<>();

    public Day() { }

    public Day(LocalDate localDate, ApplicationUser user) {
        this.setDate(localDate);
        this.setUser(user);
        for(EnumCategory e : EnumCategory.values()) {
            categories.put(e,new Category<>(e.getMin(),e.getMax()));
        }
    }

    public Day(String date, ApplicationUser user) {
        this.setDate(date);
        this.setUser(user);
        for(EnumCategory e : EnumCategory.values()) {
            categories.put(e,new Category<>(e.getMin(),e.getMax()));
        }
    }

    public Category<FoodItem> getCategory(EnumCategory enumCategory){
        return this.categories.get(enumCategory);
    }

    public Category<FoodItem> getCategory(String string){
        return this.getCategory(EnumCategory.valueOf(string.toUpperCase()));
    }

    public int getPointsCategory(EnumCategory enumCategory){
        return categories.get(enumCategory).getTotalPoints();
    }

    public int getPointsCategory(String string) {
        return this.getPointsCategory(EnumCategory.valueOf(string.toUpperCase()));
    }

    public void add(EnumCategory category, FoodItem foodItem) throws IllegalArgumentException {
        if(foodItem == null) throw new IllegalArgumentException("An empty FoodItem cannot be added.");
        categories.get(category).add(foodItem);
    }

    public Map<EnumCategory, Category<FoodItem>> getFoodItems() {
        return this.categories;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) throws DateTimeParseException {
        if(date == null || date.trim().isEmpty() || date.equals("null")) {
            this.date = LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy/MM/dd][dd/MM/yyyy][dd-MM-yyyy]");
            try {
                this.date = LocalDate.parse(date, formatter);
            } catch(DateTimeParseException e) {
                this.date = LocalDate.now();
            }
        }
    }

    private void setUser(ApplicationUser user) {
        if(user == null) throw new IllegalArgumentException("A day was created without a user");
        this.user = user;
    }

    public void removeLast(EnumCategory category) {
        categories.get(category).removeLast();
    }
}
