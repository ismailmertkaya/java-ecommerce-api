# 🛒 E-Commerce Order Management API — Spring Boot

A real-world e-commerce backend handling products, orders, and inventory management. When an order is placed, stock is automatically reduced. When cancelled, it's restored.

## 🚀 Features
- Product catalog with category filtering and stock tracking
- Order placement with automatic stock validation
- Order status lifecycle: `PENDING → CONFIRMED → SHIPPED → DELIVERED`
- Stock restoration on order cancellation
- Conflict error when ordering out-of-stock items

## 🛠️ Tech Stack
`Java 17` · `Spring Boot 3.2` · `Spring Validation` · `Maven`

## ▶️ Run
```bash
./mvnw spring-boot:run
# API: http://localhost:8080
```

## 📡 API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | All products |
| GET | `/api/products?category=Electronics` | Filter by category |
| GET | `/api/products/in-stock` | In-stock only |
| POST | `/api/products` | Add product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Remove product |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | All orders |
| GET | `/api/orders?email=x@x.com` | Orders by customer |
| GET | `/api/orders?status=PENDING` | Orders by status |
| POST | `/api/orders` | Place new order |
| PUT | `/api/orders/{id}/status` | Update order status |

## 🧪 Example: Place an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Ali Yılmaz",
    "customerEmail": "ali@example.com",
    "shippingAddress": "Kadıköy, İstanbul",
    "items": {"1": 1, "2": 2}
  }'
```

## 💡 Business Rules
- Ordering more than available stock → `409 Conflict`
- Updating a `DELIVERED` or `CANCELLED` order → `400 Bad Request`
- Cancelling an order automatically restores stock

## 📚 What I Learned
- Real business logic beyond basic CRUD
- State machine pattern for order lifecycle
- Handling concurrent stock reduction
- `BigDecimal` for accurate financial calculations
