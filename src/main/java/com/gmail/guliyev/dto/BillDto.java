package com.gmail.guliyev.dto;

import com.gmail.guliyev.entity.Order;

import java.time.LocalDateTime;

public class BillDto {
    private Long id;
    private Order order;
    private Float paidSum;
    private LocalDateTime completedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(Float paidSum) {
        this.paidSum = paidSum;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
