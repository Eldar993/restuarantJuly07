package com.gmail.guliyev.entity;

import com.gmail.guliyev.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderDish> dishes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime order_time) {
        this.createdAt = order_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void addDish(Dish dish, Long count) {
        final Optional<OrderDish> found = dishes.stream()
                .filter(d -> Objects.equals(dish, d.getDish()))
                .findFirst();

        final OrderDish orderDish;
        if (found.isEmpty()) {
            orderDish = new OrderDish(this, dish);
            dishes.add(orderDish);
        } else {
            orderDish = found.get();
        }

        orderDish.addCount(count);

        if (orderDish.getCount() == 0) {
            removeDish(dish);
        }
    }

    public void removeDish(Dish dish) {
        for (Iterator<OrderDish> iterator = dishes.iterator();
             iterator.hasNext(); ) {
            OrderDish orderDish = iterator.next();

            if (orderDish.getOrder().equals(this) &&
                    orderDish.getDish().equals(dish)) {
                iterator.remove();
                orderDish.setOrder(null);
                orderDish.setDish(null);
            }
        }
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return createdAt.equals(order.createdAt) &&
                Objects.equals(user, order.user) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, user, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_time=" + createdAt +
                ", user=" + user +
                ", orderStatus=" + status +
                '}';
    }
}
