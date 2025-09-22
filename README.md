# 🧩 Microservices – Part 1

A beginner-friendly implementation of **Microservices Architecture** using Spring Boot. This project demonstrates service discovery, inter-service communication, and fault tolerance patterns.

---

## 🛠️ Tech Stack

| Technology | Purpose | Why We Use It |
|------------|---------|---------------|
| **Java 17** | Programming Language | Modern, stable, enterprise-ready |
| **Spring Boot** | Framework | Simplifies microservice development |
| **Eureka Server** | Service Discovery | Services can find each other automatically |
| **Eureka Client** | Service Registration | Each service registers with Eureka |
| **OpenFeign** | HTTP Client | Easy way to call other services |
| **RestTemplate** | HTTP Client | Traditional way to make HTTP calls |
| **Resilience4J** | Fault Tolerance | Protects services from failures |
| **Maven** | Build Tool | Manages dependencies and builds project |

---

## 🎯 Key Points of Tech Stack

### 🔍 **Service Discovery (Eureka)**
- **What**: A registry where all services register themselves
- **Why**: Services can find each other without hardcoding IP addresses
- **How**: Services register on startup and send heartbeats

### ⚖️ **Load Balancing**
- **What**: Distributes requests across multiple service instances
- **Why**: Better performance and fault tolerance
- **How**: Eureka Client automatically balances load

### 🌐 **Inter-Service Communication**
- **OpenFeign**: Declarative HTTP client (easier to use)
- **RestTemplate**: Programmatic HTTP client (more control)

### 🛡️ **Fault Tolerance (Resilience4J)**
- **Circuit Breaker**: Stops calling failing services
- **Rate Limiter**: Controls request rate to prevent overload
- **Retry**: Automatically retries failed requests

---

## 📁 Folder Structure

```
MicroServices_Part-1/
│
├── eureka-server/                    # Service Registry (Port: 8761)
│   ├── src/main/java/
│   │   └── EurekaServerApplication.java    # Main class with @EnableEurekaServer
│   ├── src/main/resources/
│   │   └── application.yml               # Eureka server configuration
│   └── pom.xml                          # Dependencies for Eureka Server
│
├── user-service/                     # Manages Users (Port: 8081)
│   ├── src/main/java/
│   │   ├── controller/
│   │   │   └── UserController.java        # REST endpoints for users
│   │   ├── service/
│   │   │   └── UserService.java          # Business logic
│   │   ├── model/
│   │   │   └── User.java                 # User entity
│   │   └── UserServiceApplication.java   # Main class with @EnableEurekaClient
│   ├── src/main/resources/
│   │   └── application.yml               # Service config + Eureka client
│   └── pom.xml                          # Dependencies
│
├── order-service/                    # Manages Orders (Port: 8082)
│   ├── src/main/java/
│   │   ├── controller/
│   │   │   └── OrderController.java       # REST endpoints for orders
│   │   ├── service/
│   │   │   └── OrderService.java         # Business logic
│   │   ├── feign/
│   │   │   └── UserServiceClient.java    # Feign client to call User Service
│   │   ├── model/
│   │   │   └── Order.java                # Order entity
│   │   └── OrderServiceApplication.java  # Main class
│   ├── src/main/resources/
│   │   └── application.yml               # Config with Resilience4J settings
│   └── pom.xml
│
├── product-service/                  # Manages Products (Port: 8083)
│   ├── src/main/java/
│   │   ├── controller/
│   │   │   └── ProductController.java     # REST endpoints for products
│   │   ├── service/
│   │   │   └── ProductService.java       # Business logic with RestTemplate
│   │   ├── config/
│   │   │   └── RestTemplateConfig.java   # RestTemplate configuration
│   │   ├── model/
│   │   │   └── Product.java              # Product entity
│   │   └── ProductServiceApplication.java
│   └── pom.xml
│
├── notification-service/             # Sends Notifications (Port: 8084)
│   ├── src/main/java/
│   │   ├── controller/
│   │   │   └── NotificationController.java
│   │   ├── service/
│   │   │   └── NotificationService.java
│   │   └── NotificationServiceApplication.java
│   └── pom.xml
│
└── README.md                        # This file
```

---

## 🚀 How to Run

### ⚠️ **Important: Start services in this exact order!**

### **Step 1: Start Eureka Server First**
```bash
# Navigate to eureka-server directory
cd eureka-server

# Start Eureka Server
mvn spring-boot:run

# Wait until you see: "Started EurekaServerApplication"
# Verify: Open http://localhost:8761 in browser
```

### **Step 2: Start All Other Services**
Open **4 separate terminals** and run each service:

**Terminal 1 - User Service:**
```bash
cd user-service
mvn spring-boot:run
```

**Terminal 2 - Order Service:**
```bash
cd order-service
mvn spring-boot:run
```

**Terminal 3 - Product Service:**
```bash
cd product-service
mvn spring-boot:run
```

**Terminal 4 - Notification Service:**
```bash
cd notification-service
mvn spring-boot:run
```

### **Step 3: Verify Everything is Running**

1. **Check Eureka Dashboard**: http://localhost:8761
   - You should see 4 services registered

2. **Test Services**:
   ```bash
   # Test User Service
   curl http://localhost:8081/api/users
   
   # Test Order Service (calls User Service via Feign)
   curl http://localhost:8082/api/orders
   
   # Test Product Service
   curl http://localhost:8083/api/products
   
   # Test Notification Service
   curl http://localhost:8084/api/notifications
   ```

---

## 🧪 Simple Testing

### **Create a User**
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

### **Create an Order (calls User Service)**
```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"productName":"Laptop","quantity":1}'
```

### **Test Circuit Breaker**
```bash
# Make multiple rapid calls to see circuit breaker in action
for i in {1..10}; do
  curl http://localhost:8082/api/orders/test-circuit-breaker
done
```

---

## 🎯 What Each Service Does

| Service | Port | Purpose | Key Features |
|---------|------|---------|-------------|
| **Eureka Server** | 8761 | Service Registry | - Tracks all services<br/>- Provides service discovery<br/>- Shows dashboard |
| **User Service** | 8081 | User Management | - CRUD operations for users<br/>- Registers with Eureka |
| **Order Service** | 8082 | Order Processing | - CRUD operations for orders<br/>- Uses Feign to call User Service<br/>- Has Circuit Breaker |
| **Product Service** | 8083 | Product Catalog | - CRUD operations for products<br/>- Uses RestTemplate<br/>- Has Rate Limiter |
| **Notification Service** | 8084 | Send Notifications | - Sends email/SMS notifications<br/>- Simple service example |

---

## 🔧 Key Configuration Files

### **Eureka Server (application.yml)**
```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false    # Don't register itself
    fetch-registry: false          # Don't fetch registry
  server:
    enable-self-preservation: false
```

### **Microservice (application.yml)**
```yaml
server:
  port: 8081

spring:
  application:
    name: user-service            # Service name in Eureka

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka    # Eureka server URL
```

### **Resilience4J (application.yml)**
```yaml
resilience4j:
  circuitbreaker:
    instances:
      userService:
        failure-rate-threshold: 50        # Open circuit at 50% failure
        wait-duration-in-open-state: 30s  # Wait 30s before trying again
        
  ratelimiter:
    instances:
      userService:
        limit-for-period: 10              # Allow 10 requests
        limit-refresh-period: 1s          # Per 1 second
```

---

## 🚨 Common Issues & Solutions

### **Issue 1: Service not registering with Eureka**
**Solution**: Make sure Eureka Server is running first, then start other services

### **Issue 2: "Connection refused" errors**
**Solution**: Check if services are running on correct ports:
- Eureka: 8761
- User: 8081  
- Order: 8082
- Product: 8083
- Notification: 8084

### **Issue 3: Feign client not working**
**Solution**: Ensure the target service name matches exactly in Eureka registry

### **Issue 4: Port already in use**
**Solution**: Kill existing processes:
```bash
# Kill process on port 8081
lsof -ti:8081 | xargs kill -9
```

---

## 📚 What You'll Learn

- ✅ How microservices communicate with each other
- ✅ Service discovery and registration
- ✅ Load balancing between service instances  
- ✅ Handling failures with Circuit Breaker pattern
- ✅ Rate limiting to protect services
- ✅ Difference between Feign and RestTemplate
- ✅ Basic resilience patterns

---

## 🔗 Useful URLs

- **Eureka Dashboard**: http://localhost:8761
- **User Service**: http://localhost:8081/api/users
- **Order Service**: http://localhost:8082/api/orders  
- **Product Service**: http://localhost:8083/api/products
- **Notification Service**: http://localhost:8084/api/notifications

---

**🎯 Perfect for beginners who want to understand microservices step by step!**
