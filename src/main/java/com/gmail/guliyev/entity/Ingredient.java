package com.gmail.guliyev.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @Size(min = 3, max = 50)
    private String title;

    private int calories;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return calories == that.calories &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, calories);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", calories=" + calories +
                '}';
    }
}
