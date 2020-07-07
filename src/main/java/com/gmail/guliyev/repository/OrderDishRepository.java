package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.Dish;
import com.gmail.guliyev.entity.OrderDish;
import com.gmail.guliyev.entity.OrderDishId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDishRepository extends JpaRepository<OrderDish, OrderDishId> {

    void deleteAllByDish(Dish dish);
}
