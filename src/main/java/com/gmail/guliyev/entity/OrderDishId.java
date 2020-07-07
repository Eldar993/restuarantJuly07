package com.gmail.guliyev.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDishId implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "dish_id")
    private Long dishId;

    public OrderDishId() {
    }

    public OrderDishId(Long orderId, Long dishId) {
        this.orderId = orderId;
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDishId that = (OrderDishId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(dishId, that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishId);
    }
}
