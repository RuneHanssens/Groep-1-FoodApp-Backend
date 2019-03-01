package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import foodappbackend.user.ApplicationUser;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
    public ApplicationUser user;
    public Day() { }

    //@CollectionTable(name="list_of_categories")
    //private List<FoodItem> foodItems = new ArrayList<>();
    @Column(name = "categories")
    @Lob
    @ElementCollection
//    @OneToMany(cascade = CascadeType.ALL)
    private Map<EnumCategory,Category<FoodItem>> categories = new HashMap<>();

    public Day(LocalDate localDate) {
        date = localDate;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void removeLast(EnumCategory category) {
        categories.get(category).removeLast();
    }
}
