package com.gmail.guliyev.dto;

import java.util.HashMap;
import java.util.Map;

public class DishDetailDto {
    private Long id;
    private int price;
    private String name;
    private int weight;
    private Map<IngredientDto, Boolean> ingredients = new HashMap<>();
    private DishTypeDto dishType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Map<IngredientDto, Boolean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<IngredientDto, Boolean> ingredients) {
        this.ingredients = ingredients;
    }

    public DishTypeDto getDishType() {
        return dishType;
    }

    public void setDishType(DishTypeDto dishType) {
        this.dishType = dishType;
    }
}
