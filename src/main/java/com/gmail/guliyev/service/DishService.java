package com.gmail.guliyev.service;

import com.gmail.guliyev.dto.DishDetailDto;
import com.gmail.guliyev.dto.DishDto;
import com.gmail.guliyev.dto.IngredientDto;
import com.gmail.guliyev.entity.Dish;
import com.gmail.guliyev.entity.DishType;
import com.gmail.guliyev.entity.Ingredient;
import com.gmail.guliyev.repository.DishRepository;
import com.gmail.guliyev.repository.DishTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishService {
    private final DishRepository dishRepository;
    private final DishTypeRepository dishTypeRepository;
    private final IngredientService ingredientService;

    public DishService(DishRepository dishRepository,
                       DishTypeRepository dishTypeRepository,
                       IngredientService ingredientService) {
        this.dishRepository = dishRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.ingredientService = ingredientService;
    }

    public Dish addIngredient(Long dishId, Long ingredientId) {
        final Optional<Dish> dish = dishRepository.findById(dishId);
        if (dish.isEmpty()) {
//            return "Dish with id = " + dishId + "not found";
            return null;
        }
        final Ingredient ingredient = ingredientService.findById(ingredientId);
        dish.get().addIngredient(ingredient);

        return dishRepository.saveAndFlush(dish.get());
    }

    public Dish removeIngredient(Long dishId, Long ingredientId) {
        final Optional<Dish> optionalDish = dishRepository.findById(dishId);
        if (optionalDish.isEmpty()) {
//            return "Dish with id = " + dishId + "not found";
            return null;
        }
        final Ingredient ingredient = ingredientService.findById(ingredientId);
        final Dish dish = optionalDish.get();
        dish.removeIngredient(ingredient);

        return dishRepository.saveAndFlush(dish);
    }

    public static DishDto toDto(Dish dish) {
        if (dish == null) {
            return null;
        }
        DishDto result = new DishDto();
        result.setId(dish.getId());
        result.setName(dish.getName());
        result.setDishTypeId(dish.getDishType().getId());
        result.setPrice(dish.getPrice());
        result.setWeight(dish.getWeight());

        Set<Long> ingredientIds = new HashSet<>();
        for (Ingredient ingredient : dish.getIngredients()) {
            ingredientIds.add(ingredient.getId());
        }
        result.setIngredientIds(ingredientIds);

        return result;
    }

    public static List<DishDto> toDto(List<Dish> dishes) {
        return dishes
                .stream()
                .map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }


    //TODO: add implementation
    public DishDetailDto toDetailDto(Dish dish) {
        if (dish == null) {
            return null;
        }
        DishDetailDto result = new DishDetailDto();
        result.setId(dish.getId());
        result.setName(dish.getName());
        result.setPrice(dish.getPrice());
        result.setWeight(dish.getWeight());
        result.setDishType(DishTypeService.toDto(dish.getDishType()));
        final Map<IngredientDto, Boolean> ingredients = ingredientService.findAll()
                .stream()
                .map(i -> convertToDto(dish, i))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        result.setIngredients(ingredients);
        return result;
    }

    private AbstractMap.SimpleEntry<IngredientDto, Boolean> convertToDto(Dish dish, Ingredient ingredient) {
        return new AbstractMap.SimpleEntry<>(IngredientService.toDto(ingredient), dish.getIngredients().contains(ingredient));
    }

    public List<DishDetailDto> toDetailDto(List<Dish> dishes) {
        return dishes
                .stream()
                .map(entity -> toDetailDto(entity))
                .collect(Collectors.toList());
    }

    public Dish toEntity(DishDto dto) {
        if (dto == null) {
            return null;
        }
        Dish result = new Dish();
        result.setId(dto.getId());
        DishType dishType = dishTypeRepository.findById(dto.getDishTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown dish type id = "));
        result.setDishType(dishType);
        result.setName(dto.getName());
        result.setPrice(dto.getPrice());
        result.setWeight(dto.getWeight());
        final Set<Long> ingredientIds = dto.getIngredientIds();
        if (ingredientIds != null) {
            result.setIngredients(ingredientService.findById(dto.getIngredientIds()));
        }

        return result;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public boolean create(Dish dish) {
        if (dish.getId() != null) {
            return false;
        }
        dishRepository.saveAndFlush(dish);
        return true;
    }

    public Dish findById(Long id) {
        return dishRepository.findDishById(id);
    }

    public void deleteById(Long id) {
        dishRepository.deleteById(id);
    }

    public void save(Dish dish) {
        dishRepository.saveAndFlush(dish);
    }

    public Dish update(Dish dish) {
        if (dish == null) {
            return null;
        }
        Dish updatedDish = dishRepository.findById(dish.getId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown dish id"));
        if (updatedDish == null) {
            return null;
        }
        updatedDish.setDishType(dish.getDishType());
        updatedDish.setName(dish.getName());
        updatedDish.setWeight(dish.getWeight());
        updatedDish.setPrice(dish.getPrice());
        updatedDish.setIngredients(dish.getIngredients());

        final Dish result = dishRepository.saveAndFlush(updatedDish);
        return result;
    }


    public List<Dish> findAllByDishType(DishType dishType) {
        return dishRepository.findAllByDishType(dishType);
    }

    public List<Dish> findAllByIngredient(Ingredient ingredient) {
        return dishRepository.findAllByIngredientsContains(ingredient);
    }
}
