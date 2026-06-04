package com.example.ecommerce.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;


public class Product {

    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    private String category;
    private boolean active = true;

    public Product() {}

    public Product(Long id, String name, BigDecimal price, int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public boolean isInStock() { return stock > 0; }
    public boolean hasEnoughStock(int quantity) { return stock >= quantity; }

    public void reduceStock(int quantity) {
        if (!hasEnoughStock(quantity)) throw new IllegalStateException("Not enough stock");
        this.stock -= quantity;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
