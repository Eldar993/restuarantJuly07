package com.gmail.guliyev.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private DishType dishType;

    private int price;

    @Column(unique = true)
    private String name;

    private int weight;

    @ManyToMany
    @JoinTable(
            name = "dishes_ingredients",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;

    public Long getId() {
        return id;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String dish) {
        this.name = dish;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        ingredients.add(ingredient);
    }

    public boolean removeIngredient(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return price == dish.price &&
                weight == dish.weight &&
                Objects.equals(dishType, dish.dishType) &&
                Objects.equals(name, dish.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishType, price, name, weight);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", dishType=" + dishType +
                ", price=" + price +
                ", dish='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
