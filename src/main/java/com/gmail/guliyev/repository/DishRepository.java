package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.Dish;
import com.gmail.guliyev.entity.DishType;
import com.gmail.guliyev.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAllByDishType(DishType dishType);

    List<Dish> findAllByIngredientsContains(Ingredient ingredient);

    Dish findDishById(Long id);
}
