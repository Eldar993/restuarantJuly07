package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Dish, Long> {
}
