package com.example.restaurant.repository;

import com.example.restaurant.entity.Dish;
import com.example.restaurant.entity.OrderDish;
import com.example.restaurant.entity.OrderDishId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDishRepository extends JpaRepository<OrderDish, OrderDishId> {

    void deleteAllByDish(Dish dish);
}
