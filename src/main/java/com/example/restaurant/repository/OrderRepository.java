package com.example.restaurant.repository;

import com.example.restaurant.entity.Dish;
import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.User;
import com.example.restaurant.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);

    Optional<Order> findFirstByUserAndStatus(User user, OrderStatus status);

    Long countAllByUserAndStatusNot(User user, OrderStatus status);

    List<Order> findAllByUser(User user);

    List<Order> findByUserAndStatus(User user, OrderStatus status);

    List<Order> findByStatus(OrderStatus status);

    void deleteByUser(User user);

    @Query("select od.order from OrderDish od " +
            "where od.dish = ?1")
    Set<Order> findAllByDish(Dish dish);
}
