package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day_table")
public class Day {
    @Id
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL)
    @CollectionTable(name="list_of_categories")
    private List<FoodItem> foodItems = new ArrayList<>();

    public Day() {
        creationDate = LocalDate.now();
    }

    public Day(LocalDate localDate) {
        creationDate = localDate;
    }

    public ArrayList<FoodItem> getCategory(EnumCategory enumCategory){
        ArrayList<FoodItem> categories = new ArrayList<>();
        for (FoodItem c: this.foodItems) {
            if (c.enumCategory == enumCategory){
                categories.add(c);
            }
        }
//        if(category == null){
//            throw new CategoryException("Deze categorie is niet aanwezig in het dag object!");
//        }
        return categories;
    }
    public int getPointsCategory(EnumCategory enumCategory){
        int points = 0;
        for (FoodItem c: foodItems) {
            //System.out.println("zit in getPoints for categorie");
            if (c.enumCategory == enumCategory){
                points += c.getPoints();
            }
        }
        return points;
    }
    public void add(FoodItem foodItem){
        foodItems.add(foodItem);
    }

    public List<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    public LocalDate getDate() {
        return creationDate;
    }
    //        return creationDate;
//    public LocalDate getCreationDate() {

//    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
