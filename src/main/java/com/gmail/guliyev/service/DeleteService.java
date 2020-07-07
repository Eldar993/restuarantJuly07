package com.gmail.guliyev.service;

import com.gmail.guliyev.entity.Dish;
import com.gmail.guliyev.entity.DishType;
import com.gmail.guliyev.entity.Ingredient;
import com.gmail.guliyev.entity.Order;
import com.gmail.guliyev.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class DeleteService {
    private final UserService userService;
    private final OrderService orderService;
    private final DishTypeService dishTypeService;
    private final DishService dishService;
    private final IngredientService ingredientService;

    public DeleteService(UserService userService,
                         OrderService orderService,
                         DishTypeService dishTypeService,
                         DishService dishService, IngredientService ingredientService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dishTypeService = dishTypeService;
        this.dishService = dishService;
        this.ingredientService = ingredientService;
    }

    public void deleteUserById(Long userId) {
        final User user = userService.findUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: id = " + userId));
        orderService.deleteByUser(user);
        userService.delete(userId);
    }

    public void deleteDishTypeById(Long dishTypeId) {
        final DishType dishType = dishTypeService.findById(dishTypeId);
        dishService.findAllByDishType(dishType)
                .forEach(d -> {
                    orderService.deleteDishFromOrder(d);
                    dishService.deleteById(d.getId());
                });

        dishTypeService.deleteById(dishTypeId);
    }

    public void deleteIngredientById(Long ingredientId) {
        final Ingredient ingredient = ingredientService.findById(ingredientId);
        dishService.findAllByIngredient(ingredient)
                .forEach(d -> {
                    d.getIngredients().remove(ingredient);
                    dishService.save(d);
               });
        ingredientService.deleteById(ingredientId);
    }

    public void deleteDishById(Long dishId){
        final Dish dish = dishService.findById(dishId);
        Set<Order> orders = orderService.findAllByDish(dish);
        orders
                .forEach(o -> {
                    o.removeDish(dish);
                    orderService.save(o);
                });
        dishService.deleteById(dishId);
    }

}
