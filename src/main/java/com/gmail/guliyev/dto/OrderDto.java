package com.gmail.guliyev.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Long id;
    private LocalDateTime createdAt;
    private UserDto user;
    private List<DishSimpleDto> dishes = new ArrayList<>();
    private String orderStatus;
    private long totalPrice;

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    private boolean needPayment = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<DishSimpleDto> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishSimpleDto> dishes) {
        this.dishes = dishes;
    }

    public boolean isNeedPayment() {
        return needPayment;
    }

    public void setNeedPayment(boolean needPayment) {
        this.needPayment = needPayment;
    }
}
