package com.example.restaurant.repository;

import com.example.restaurant.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findIngredientById(Long id);

    List<Ingredient> findAll();

    void deleteById(Long id);
}
