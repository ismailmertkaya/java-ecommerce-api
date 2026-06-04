package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final Map<Long, Product> storage = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public ProductRepository() {
        // Seed with some sample products
        save(new Product(null, "Laptop", new java.math.BigDecimal("15000.00"), 10, "Electronics"));
        save(new Product(null, "Wireless Mouse", new java.math.BigDecimal("350.00"), 50, "Electronics"));
        save(new Product(null, "Java Programming Book", new java.math.BigDecimal("120.00"), 30, "Books"));
        save(new Product(null, "USB-C Hub", new java.math.BigDecimal("450.00"), 25, "Electronics"));
        save(new Product(null, "Desk Lamp", new java.math.BigDecimal("200.00"), 15, "Office"));
    }

    public List<Product> findAll() { return new ArrayList<>(storage.values()); }

    public Optional<Product> findById(Long id) { return Optional.ofNullable(storage.get(id)); }

    public Product save(Product product) {
        if (product.getId() == null) product.setId(counter.getAndIncrement());
        storage.put(product.getId(), product);
        return product;
    }

    public boolean deleteById(Long id) { return storage.remove(id) != null; }

    public List<Product> findByCategory(String category) {
        return storage.values().stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .collect(Collectors.toList());
    }

    public List<Product> findInStock() {
        return storage.values().stream()
                .filter(Product::isInStock)
                .collect(Collectors.toList());
    }
}
