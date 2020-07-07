package com.gmail.guliyev.service;

import com.gmail.guliyev.dto.IngredientDto;
import com.gmail.guliyev.entity.Ingredient;
import com.gmail.guliyev.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
//TODO: Change returned types and input methods arguments from Dto to Entity
//      convert entity to dto in Controller layer
public class IngredientService {

    private final IngredientRepository ingredientRepository;


    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public boolean create(Ingredient ingredient) {
        if (ingredient.getId() != null) {
            return false;
        }
        ingredientRepository.saveAndFlush(ingredient);
        return true;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient findById(Long id) {
        Ingredient ingredient = ingredientRepository.findIngredientById(id);
        return ingredient;
    }

    public Set<Ingredient> findById(Set<Long> ids) {
        return ids.stream()
                .map(this::findById)
                .peek(i -> {
                    if (i == null) {
                        throw new IllegalArgumentException("Found unknown ingredient id");
                    }
                })
                .collect(Collectors.toSet());
    }

    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }

    public Ingredient update(Ingredient ingredient) {
        Ingredient updatedIngredient = ingredientRepository.findIngredientById(ingredient.getId());
        if (updatedIngredient == null) {
            return null;
        }
        updatedIngredient.setTitle(ingredient.getTitle());
        updatedIngredient.setCalories(ingredient.getCalories());

        final Ingredient result = ingredientRepository.saveAndFlush(updatedIngredient);
        return result;
    }

    //TODO: make converter's methods static
    public static Ingredient toEntity(IngredientDto dto) {
        if (dto == null) {
            return null;
        }
        Ingredient result = new Ingredient();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        result.setCalories(dto.getCalories());

        return result;
    }

    public static Set<Ingredient> toEntity(Set<IngredientDto> dto) {
        if (dto == null || dto.isEmpty()) {
            return Collections.emptySet();
        }

        return dto.stream()
                .map(IngredientService::toEntity)
                .collect(Collectors.toSet());
    }

    public static Set<IngredientDto> toDto(Set<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(IngredientService::toDto)
                .collect(Collectors.toSet());
    }

    public static List<IngredientDto> toDto(List<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(IngredientService::toDto)
                .collect(Collectors.toList());
    }

    public static IngredientDto toDto(Ingredient entity) {
        if (entity == null) {
            return null;
        }
        IngredientDto result = new IngredientDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setCalories(entity.getCalories());

        return result;
    }
}
