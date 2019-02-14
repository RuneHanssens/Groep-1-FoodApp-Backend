package foodappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


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
    private List<Category> categories;

    public Day() {
        creationDate = LocalDate.now();
        categories = new ArrayList<Category>();
    }

    public Day(LocalDate localDate) {
        creationDate = localDate;
        categories = new ArrayList<Category>();
    }

    public ArrayList<Category> getCategory(EnumCategory enumCategory){
        ArrayList<Category> categories = new ArrayList<>();
        for (Category c: this.categories) {
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
        for (Category c: categories) {
            //System.out.println("zit in getPoints for categorie");
            if (c.enumCategory == enumCategory){
                points += c.getPoints();
            }
        }
        return points;
    }
    public void add(Category category){
        categories.add(category);
    }

    public LocalDate getDate() {
        return creationDate;
    }

//    public LocalDate getCreationDate() {
//        return creationDate;
//    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

}
