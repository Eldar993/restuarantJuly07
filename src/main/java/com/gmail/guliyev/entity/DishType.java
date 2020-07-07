package com.gmail.guliyev.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dish_types")
public class DishType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String title;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishType dishType = (DishType) o;
        return title.equals(dishType.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "DishType{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
