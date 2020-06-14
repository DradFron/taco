package com.example.taco.repository;

import com.example.taco.pojo.Order;

public interface OrderRepository {
    Order save(Order order);
}
