package com.gmail.guliyev.service;

import com.gmail.guliyev.dto.DishSimpleDto;
import com.gmail.guliyev.dto.OrderDto;
import com.gmail.guliyev.dto.UserDto;
import com.gmail.guliyev.entity.Dish;
import com.gmail.guliyev.entity.Order;
import com.gmail.guliyev.entity.OrderDish;
import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.OrderStatus;
import com.gmail.guliyev.repository.OrderDishRepository;
import com.gmail.guliyev.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    protected final OrderDishRepository orderDishRepository;
    private final DishService dishService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository,
                        OrderDishRepository orderDishRepository,
                        DishService dishService,
                        UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDishRepository = orderDishRepository;
        this.dishService = dishService;
        this.userService = userService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order update(Order order) {
        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        if (updatedOrder.isEmpty()) {
            return null;
        }
        updatedOrder.get().setId(order.getId());
        updatedOrder.get().setCreatedAt(order.getCreatedAt());
        updatedOrder.get().setStatus(order.getStatus());
        updatedOrder.get().setUser(order.getUser());

        final Order result = orderRepository.saveAndFlush(updatedOrder.get());
        return result;
    }

    public static OrderDto toDto(Order entity) {
        if (entity == null) {
            return null;
        }
        OrderDto result = new OrderDto();
        result.setId(entity.getId());
        result.setCreatedAt(entity.getCreatedAt());
        result.setUser(UserService.toDto(entity.getUser()));
        result.setOrderStatus(entity.getStatus().toString());
        result.setNeedPayment(OrderStatus.WAIT_PAYMENT == entity.getStatus());
        result.setTotalPrice(calculateTotalPrice(entity));

        final List<DishSimpleDto> dishes = entity.getDishes()
                .stream()
                .map(od -> OrderService.toSimpleDto(od))
                .collect(Collectors.toList());
        result.setDishes(dishes);

        return result;
    }

    public static DishSimpleDto toSimpleDto(OrderDish orderDish) {
        if (orderDish == null) {
            return null;
        }

        final Dish dish = orderDish.getDish();
        DishSimpleDto result = new DishSimpleDto();
        result.setId(dish.getId());
        result.setName(dish.getName());
        result.setDishType(dish.getDishType().getTitle());
        result.setPrice(dish.getPrice());
        result.setCount(orderDish.getCount());


        return result;
    }

    public static List<OrderDto> toDto(List<Order> orders) {
        return orders
                .stream()
                .map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        Order result = new Order();
        result.setId(dto.getId());
        result.setCreatedAt(dto.getCreatedAt());
        result.setStatus(OrderStatus.valueOf(dto.getOrderStatus()));
        final UserDto userDto = dto.getUser();
        if (userDto == null) {
            throw new IllegalArgumentException("Unknown user");
        }
        final String username = userDto.getName();
        final User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User with name = '" + username + "' not found"));
        result.setUser(user);
        return result;
    }

    //to increase and decrease dish count in order
    public void addDish(String username, Long dishId, Long count) {
        final User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User with [name='" + username + "'] not found"));
        final Dish dish = dishService.findById(dishId);
/*
                .orElseThrow(() -> new IllegalArgumentException("Dish with [id='" + dishId + "'] not found"));
*/

        final Order order = orderRepository.findFirstByUserAndStatus(user, OrderStatus.NEW)
                .orElseGet(() -> create(user));

        order.addDish(dish, count);
        orderRepository.saveAndFlush(order);
    }

    public Order create(User user) {
        final Long count = orderRepository.countAllByUserAndStatusNot(user, OrderStatus.DONE);
        if (count > 0) {
            throw new IllegalStateException("User has uncompleted order, order in progress or some unpaid order");
        }
        final Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);


        return orderRepository.saveAndFlush(order);
    }

    //to increase and decrease dish count in order
    public void remove(Long orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));
    }

    public void removeDish(Long orderId, Long dishId) {
        Order order = orderRepository.findOrderById(orderId);
        Dish dish = dishService.findById(dishId)
/*
                .orElseThrow(() -> new IllegalArgumentException("Dish with [id='" + dishId + "'] not found"));
*/
        ;
        order.removeDish(dish);
        orderRepository.saveAndFlush(order);
    }

    public List<Order> findAllByUser(String username) {
        final User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User with [name='" + username + "'] not found"));
        final List<Order> orders = orderRepository.findAllByUser(user);

        return orders;
    }

    public Order findByStatus(String username, OrderStatus status) {
        final User user = userService.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User with [name='" + username + "'] not found"));
        final List<Order> orders = orderRepository.findByUserAndStatus(user, status);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No order with status=" + status);
        } else if (orders.size() > 1) {
            throw new IllegalArgumentException("Too many orders with status=" + status);
        }

        return orders.get(0);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order findUnpaid(String username) {
        return this.findByStatus(username, OrderStatus.WAIT_PAYMENT);
    }

    public void confirmOrder(String username) {
        changeOrderStatus(username, OrderStatus.NEW, OrderStatus.IN_PROGRESS);
    }

    public void cookCompleted(String username) {
        changeOrderStatus(username, OrderStatus.IN_PROGRESS, OrderStatus.WAIT_PAYMENT);
    }

    public Optional<String> complete(String username, long payment) {
        //    TODO:
        //      1) order = find order in WAIT_PAYMENT status for USER with name 'username' (use findByStatus(String, OrderStatus) method)
        //      2) validate payment (should be greater than total price for WAIT_PAYMENT order)
        //      3) if 2) was success: change status to DONE and saveAndFlush

        Order order = findByStatus(username, OrderStatus.WAIT_PAYMENT);
        if (payment < calculateTotalPrice(order)) {
            return Optional.of("Not enough money. Increase your payment");
        }

        order.setStatus(OrderStatus.DONE);
        orderRepository.saveAndFlush(order);
        return Optional.empty();
    }

    private void changeOrderStatus(String username, OrderStatus status, OrderStatus nextStatus) {
        final Order order = findByStatus(username, status);
        order.setStatus(nextStatus);

        orderRepository.saveAndFlush(order);
    }

    public static long calculateDishPrice(OrderDish dish) {
        return dish.getCount() * dish.getDish().getPrice();
    }

    public static long calculateTotalPrice(Order order) {
        long sum = 0L;
        for (OrderDish orderDish : order.getDishes()) {
            sum += orderDish.getDish().getPrice() * orderDish.getCount();
        }
        return sum;
    }

    public void deleteByUser(User user) {
        orderRepository.deleteByUser(user);
    }

    public void deleteDishFromOrder(Dish dish) {
        orderDishRepository.deleteAllByDish(dish);
    }

    public Set<Order> findAllByDish(Dish dish) {
        return orderRepository.findAllByDish(dish);
    }

    public void save(Order d) {
        orderRepository.saveAndFlush(d);
    }
}
