package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodappbackend.user.ApplicationUser;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "day_table")
public class Day {
    @Id
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
//    @OneToMany(cascade = CascadeType.ALL)
    //@CollectionTable(name="list_of_categories")
    //private List<FoodItem> foodItems = new ArrayList<>();
    @Lob
    @ElementCollection
    private Map<EnumCategory,Category<FoodItem>> categories = new HashMap<>();

    public Day() {
        this(LocalDate.now());
    }

    public Day(LocalDate localDate) {
        date = localDate;
        for(EnumCategory e : EnumCategory.values()) {
            categories.put(e,new Category<>(e.getMin(),e.getMax()));
        }
    }

    public Category<FoodItem> getCategory(EnumCategory enumCategory){
        return categories.get(enumCategory);
    }

    public int getPointsCategory(EnumCategory enumCategory){
        return categories.get(enumCategory).getTotalPoints();
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void removeLast(EnumCategory category) {
        categories.get(category).removeLast();
    }
}
