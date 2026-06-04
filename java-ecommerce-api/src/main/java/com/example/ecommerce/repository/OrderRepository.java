package com.example.ecommerce.repository;

import com.example.ecommerce.model.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {

    private final Map<Long, Order> storage = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Order> findAll() { return new ArrayList<>(storage.values()); }

    public Optional<Order> findById(Long id) { return Optional.ofNullable(storage.get(id)); }

    public Order save(Order order) {
        if (order.getId() == null) order.setId(counter.getAndIncrement());
        storage.put(order.getId(), order);
        return order;
    }

    public List<Order> findByCustomerEmail(String email) {
        return storage.values().stream()
                .filter(o -> email.equalsIgnoreCase(o.getCustomerEmail()))
                .collect(Collectors.toList());
    }

    public List<Order> findByStatus(Order.Status status) {
        return storage.values().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
    }
}
