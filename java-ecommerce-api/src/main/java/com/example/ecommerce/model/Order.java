package com.example.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order — a customer's purchase request containing one or more items.
 */
public class Order {

    public enum Status {
        PENDING,    // just created, payment not confirmed
        CONFIRMED,  // payment confirmed
        SHIPPED,    // on the way
        DELIVERED,  // received by customer
        CANCELLED   // cancelled
    }

    private Long id;
    private String customerName;
    private String customerEmail;
    private List<OrderItem> items = new ArrayList<>();
    private Status status = Status.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String shippingAddress;

    public Order() {}

    public Order(Long id, String customerName, String customerEmail, String shippingAddress) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
    }

    /** Calculate total price of all items in this order */
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    public void addItem(OrderItem item) { items.add(item); }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
