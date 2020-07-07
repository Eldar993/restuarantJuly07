package com.gmail.guliyev.dto;

import java.util.Set;

public class DishDto {
    private Long id;
    private Long dishTypeId;
    private int price;
    private String name;
    private int weight;
    private Set<Long> ingredientIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(Long dishTypeId) {
        this.dishTypeId = dishTypeId;
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

    public Set<Long> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(Set<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
