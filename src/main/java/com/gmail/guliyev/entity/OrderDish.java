package com.gmail.guliyev.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_dish")
public class OrderDish {

    @EmbeddedId
    private OrderDishId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private Order order;

    private Long count = 0L;

    public OrderDish() {

    }

    public OrderDish(Order order, Dish dish) {
        this.order = order;
        this.dish = dish;
        this.id = new OrderDishId(order.getId(), dish.getId());
    }

    public OrderDishId getId() {
        return id;
    }

    public void setId(OrderDishId id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Order getOrder() {
        return order;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addCount(Long count) {
        this.count += count;
        if (count < 0) {
            throw new IllegalArgumentException("Wrong count");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDish orderDish = (OrderDish) o;
        return Objects.equals(id, orderDish.id) &&
                Objects.equals(dish, orderDish.dish) &&
                Objects.equals(order, orderDish.order) &&
                Objects.equals(count, orderDish.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dish, order, count);
    }
}
