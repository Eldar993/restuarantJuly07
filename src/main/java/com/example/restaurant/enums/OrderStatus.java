package com.example.restaurant.enums;

public enum OrderStatus {
    NEW,        //когда добавляются блюда в корзину, но пользователь еще не сделал заказ
    IN_PROGRESS, //пользователь подтвердил заказ в корзине, но ему еще не приготовили (пользователь с ролью ПОВАР не отметил что заказ готов)
    WAIT_PAYMENT,
    DONE
}
