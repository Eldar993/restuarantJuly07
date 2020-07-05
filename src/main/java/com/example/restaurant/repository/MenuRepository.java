package com.example.restaurant.repository;

import com.example.restaurant.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Dish, Long> {
}
