package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishTypeRepository extends JpaRepository<DishType, Long> {

    void deleteById(Long id);

    DishType findDishTypeById(Long id);
}
