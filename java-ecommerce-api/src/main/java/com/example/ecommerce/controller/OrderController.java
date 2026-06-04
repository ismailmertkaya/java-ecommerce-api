package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Order.Status status) {
        if (email != null) return ResponseEntity.ok(orderService.getOrdersByEmail(email));
        if (status != null) return ResponseEntity.ok(orderService.getOrdersByStatus(status));
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("customerName");
        String email = (String) body.get("customerEmail");
        String address = (String) body.get("shippingAddress");

        @SuppressWarnings("unchecked")
        Map<String, Integer> rawItems = (Map<String, Integer>) body.get("items");
        Map<Long, Integer> items = new java.util.HashMap<>();
        rawItems.forEach((k, v) -> items.put(Long.parseLong(k), v));

        Order order = orderService.placeOrder(name, email, address, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        Order.Status newStatus = Order.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(orderService.updateStatus(id, newStatus));
    }
}
