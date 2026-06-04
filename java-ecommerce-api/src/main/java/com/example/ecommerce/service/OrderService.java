package com.example.ecommerce.service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Core business logic:
 * - When an order is placed, stock is automatically reduced
 * - When an order is cancelled, stock is restored
 * - Status transitions are validated
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() { return orderRepository.findAll(); }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    /**
     * Place a new order.
     * Request body: { customerName, customerEmail, shippingAddress, items: [{productId, quantity}] }
     */
    public Order placeOrder(String customerName, String customerEmail,
                            String shippingAddress, Map<Long, Integer> productQuantities) {

        Order order = new Order(null, customerName, customerEmail, shippingAddress);

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

            if (!product.hasEnoughStock(quantity)) {
                throw new InsufficientStockException(
                        "Not enough stock for: " + product.getName() +
                        " (requested: " + quantity + ", available: " + product.getStock() + ")"
                );
            }

            // Reduce stock and create order item
            product.reduceStock(quantity);
            productRepository.save(product);

            order.addItem(new OrderItem(product.getId(), product.getName(), quantity, product.getPrice()));
        }

        return orderRepository.save(order);
    }

    /** Update order status (PENDING → CONFIRMED → SHIPPED → DELIVERED) */
    public Order updateStatus(Long orderId, Order.Status newStatus) {
        Order order = getOrderById(orderId);

        // Can't reactivate a delivered or cancelled order
        if (order.getStatus() == Order.Status.DELIVERED || order.getStatus() == Order.Status.CANCELLED) {
            throw new IllegalStateException("Cannot update a " + order.getStatus() + " order");
        }

        order.setStatus(newStatus);

        // Restore stock if order is cancelled
        if (newStatus == Order.Status.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                productRepository.findById(item.getProductId()).ifPresent(product -> {
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.save(product);
                });
            }
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    public List<Order> getOrdersByStatus(Order.Status status) {
        return orderRepository.findByStatus(status);
    }
}
