package com.example.ecommerce.model;

import java.math.BigDecimal;

/**
 * OrderItem — a single product line within an order.
 * Captures the price at time of purchase (price may change later).
 */
public class OrderItem {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice; // price at time of order

    public OrderItem() {}

    public OrderItem(Long productId, String productName, int quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /** quantity × unitPrice */
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters & Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
