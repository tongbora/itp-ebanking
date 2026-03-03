# 🚀 ITP E-Banking Project - START HERE

## Welcome to the ITP E-Banking System!

This is a **production-grade microservices** project implementing:
- ✅ **Event Sourcing** with Axon Framework
- ✅ **CQRS Pattern** (Command Query Responsibility Segregation)
- ✅ **Microservices Architecture** with Spring Cloud
- ✅ **API Gateway** with Spring Cloud Gateway
- ✅ **Service Discovery** with Eureka
- ✅ **Centralized Configuration** with Config Server
- ✅ **Event Streaming** with Kafka
- ✅ **Change Data Capture** with Debezium

---

## 📚 Documentation Files

### 1. **README_IMPLEMENTATION_FLOW.md** ⭐ **[START HERE]**
**672 lines** of comprehensive documentation covering:
- Complete architecture overview with diagrams
- 7 implementation phases with full code examples
- Step-by-step request-response flow (15 steps)
- Technology stack details
- All design patterns explained
- Quick start commands
- Port reference guide

**👉 This is your main guide to understand the entire system!**

---

## ⚡ Quick Start (10 Minutes)

### Prerequisites
- Java 21
- Docker & Docker Compose
- Gradle (comes with project)

### Step 1: Start Infrastructure (2 minutes)
```bash
# Create Docker network
docker network create itp-net

# Start PostgreSQL databases
cd deployment/itp-postgres
docker-compose up -d
```

### Step 2: Start Spring Cloud Services (4 minutes)
Open 4 terminal windows:

**Terminal 1 - Eureka Server** (Service Registry)
```bash
cd spring-cloud/eureka-server
./gradlew bootRun
# Wait for: "Started EurekaServerApplication"
# Access: http://localhost:8761
```

**Terminal 2 - Config Server** (Configuration Management)
```bash
cd spring-cloud/config-server
./gradlew bootRun
# Wait for: "Started ConfigServerApplication"
# Access: http://localhost:8888
```

**Terminal 3 - IAM Service** (OAuth2)
```bash
cd spring-cloud/iam-service
./gradlew bootRun
# Wait for: "Started IamServiceApplication"
# Access: http://localhost:9090
```

**Terminal 4 - Gateway Server** (API Gateway)
```bash
cd spring-cloud/gateway-server
./gradlew bootRun
# Wait for: "Started GatewayServerApplication"
# Access: http://localhost:8080
```

### Step 3: Start Microservices (4 minutes)
Open 3 more terminal windows:

**Terminal 5 - Customer Service**
```bash
cd microservices/customer-service
./gradlew bootRun
# Wait for: "Started CustomerServiceApplication"
# Port: 20260
```

**Terminal 6 - Account Service**
```bash
cd microservices/account-service
./gradlew bootRun
# Port: 20261
```

**Terminal 7 - Pipeline Service**
```bash
cd microservices/pipeline-service
./gradlew bootRun
# Port: 20262
```

### Step 4: Verify Everything is Running
```bash
# Check Eureka Dashboard - should show all registered services
open http://localhost:8761

# Test Customer Service API
curl http://localhost:8080/api/customers?pageNumber=0&pageSize=10
```

---

## 🎯 Understanding the Architecture

### The Big Picture
```
Client → Gateway (8080) → Microservices → Database/Events
           ↓
     Eureka (8761) - Service Discovery
     Config (8888) - Configuration
     IAM (9090)    - Security
```

### Request Flow Example: Create Customer
```
1. Client sends POST to Gateway (8080)
2. Gateway validates JWT with IAM Service
3. Gateway routes to Customer Service (20260) via Eureka
4. Customer Service executes CQRS Command
5. Axon creates CustomerCreatedEvent
6. Event stored in Event Store
7. Event published to Kafka
8. Projection updates PostgreSQL (read model)
9. Response sent back to client
10. Other services react to event (async)
```

---

## 🏗️ Project Structure

```
itp-ebanking/
├── 📦 common/                    # Shared value objects (CustomerId, AccountId, etc.)
├── 🔧 microservices/
│   ├── customer-service/         # Customer domain with Event Sourcing & CQRS
│   ├── account-service/          # Account domain (similar pattern)
│   └── pipeline-service/         # Integration between services
├── ☁️ spring-cloud/
│   ├── eureka-server/            # Service Discovery
│   ├── config-server/            # Centralized Config
│   ├── gateway-server/           # API Gateway
│   └── iam-service/              # OAuth2 Authorization
├── ⚙️ config-repo/                # External configuration files
├── 🐳 deployment/                # Docker infrastructure
└── 📖 README_IMPLEMENTATION_FLOW.md  # Comprehensive guide
```

---

## 🔌 Service Ports Reference

| Service | Port | URL | Purpose |
|---------|------|-----|---------|
| **Gateway** | 8080 | http://localhost:8080 | Main API entry point |
| **Eureka** | 8761 | http://localhost:8761 | Service registry dashboard |
| **Config Server** | 8888 | http://localhost:8888 | Configuration management |
| **IAM Service** | 9090 | http://localhost:9090 | OAuth2 authorization |
| **Customer Service** | 20260 | http://localhost:20260 | Customer operations |
| **Account Service** | 20261 | http://localhost:20261 | Account operations |
| **Pipeline Service** | 20262 | http://localhost:20262 | Integration hub |
| **PostgreSQL** | 16851 | localhost:16851 | Microservices database |

---

## 📖 Learn the System (Step by Step)

### Phase 1: Understand Architecture (30 minutes)
1. Read **README_IMPLEMENTATION_FLOW.md** sections:
   - Project Overview
   - Architecture Overview
   - Project Structure

### Phase 2: Understand Event Sourcing (30 minutes)
2. Read **PHASE 2: Event Sourcing & CQRS Implementation**
3. Study the code:
   - `microservices/customer-service/src/main/java/com/tongbora/customerservice/domain/aggregate/CustomerAggregate.java`
   - Look at `@CommandHandler` and `@EventSourcingHandler`

### Phase 3: Understand CQRS (30 minutes)
4. Read **PHASE 3: Application Services & REST API**
5. Study:
   - Command Side: `CustomerController.java` (writes)
   - Query Side: `CustomerQueryController.java` (reads)

### Phase 4: Hands-On Testing (1 hour)
6. Start all services (follow Quick Start above)
7. Test the APIs:
```bash
# Create a customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": {"firstName": "John", "lastName": "Doe"},
    "email": {"value": "john@example.com"},
    "phoneNumber": "+1234567890",
    "gender": "MALE",
    "dob": "1990-01-01"
  }'

# Get all customers
curl http://localhost:8080/api/customers?pageNumber=0&pageSize=10
```

### Phase 5: Deep Dive (2+ hours)
8. Read complete **README_IMPLEMENTATION_FLOW.md** (all 7 phases)
9. Explore database schema
10. Check Eureka dashboard
11. Review configuration files

---

## 🔑 Key Concepts

### Event Sourcing
**Traditional approach:**
```
Database stores current state only:
customers = [{ id: 1, name: "John", phone: "123" }]
```

**Event Sourcing approach:**
```
Store all changes as events:
events = [
  CustomerCreatedEvent { id: 1, name: "John", phone: "123" },
  PhoneChangedEvent { id: 1, newPhone: "456" }
]
Current state = replay all events
```

**Benefits:**
- Complete audit trail
- Time travel (query past state)
- Event replay for debugging

### CQRS (Command Query Responsibility Segregation)
**Separate read and write:**
- **Commands** (Write): Modify state → Event Store
- **Queries** (Read): Read from optimized database
- Async synchronization for consistency

### Microservices
Each service:
- Has own database
- Deploys independently
- Scales independently
- Communicates via events

---

## 🧪 Testing the API

### Create Customer
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": {
      "firstName": "Jane",
      "lastName": "Smith"
    },
    "email": {
      "value": "jane.smith@example.com"
    },
    "phoneNumber": "+1987654321",
    "gender": "FEMALE",
    "dob": "1992-05-20",
    "kyc": {
      "status": "APPROVED",
      "approvedDate": "2024-01-15"
    },
    "address": {
      "street": "456 Oak Avenue",
      "city": "Los Angeles",
      "state": "CA",
      "postalCode": "90001"
    },
    "contact": {
      "emergencyContactName": "John Smith",
      "emergencyContactPhone": "+1555123456"
    }
  }'
```

### Get Customers (with pagination)
```bash
curl http://localhost:8080/api/customers?pageNumber=0&pageSize=10
```

### Update Phone Number
```bash
curl -X PUT http://localhost:8080/api/customers/{CUSTOMER_ID}/phone-number \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber": "+1222333444"}'
```

### Get Customer History
```bash
curl http://localhost:8080/api/customers/{CUSTOMER_ID}/history
```

---

## 🛠️ Development Workflow

### Adding a New Feature
1. **Define Command** in `domain/command/`
2. **Define Event** in `domain/event/`
3. **Add CommandHandler** in Aggregate
4. **Add EventSourcingHandler** in Aggregate
5. **Update Projection** for read model
6. **Add QueryHandler** (if needed)
7. **Create REST endpoint** in Controller

### Example: Add Address Change Feature
```java
// 1. Command
public record ChangeAddressCommand(
    CustomerId customerId,
    Address newAddress
) {}

// 2. Event
public record AddressChangedEvent(
    CustomerId customerId,
    Address newAddress
) {}

// 3. In CustomerAggregate
@CommandHandler
public void handle(ChangeAddressCommand cmd) {
    AggregateLifecycle.apply(
        new AddressChangedEvent(cmd.customerId(), cmd.newAddress())
    );
}

// 4. Event Handler
@EventSourcingHandler
public void on(AddressChangedEvent event) {
    this.address = event.newAddress();
}

// 5. In Projection
@EventHandler
public void on(AddressChangedEvent event) {
    CustomerEntity entity = repository.findById(event.customerId()).get();
    entity.setAddress(event.newAddress());
    repository.save(entity);
}

// 6. In Controller
@PutMapping("/{customerId}/address")
public ChangeAddressResponse changeAddress(
    @PathVariable UUID customerId,
    @RequestBody Address newAddress) {
    return service.changeAddress(customerId, newAddress);
}
```

---

## 🐛 Troubleshooting

### Services won't start
```bash
# Check if ports are already in use
lsof -i :8761  # Eureka
lsof -i :8888  # Config Server
lsof -i :8080  # Gateway

# Kill processes if needed
kill -9 <PID>
```

### Database connection issues
```bash
# Check PostgreSQL is running
docker ps | grep postgres

# Test connection
PGPASSWORD=itp@168 psql -h localhost -p 16851 -U itpusr -d db_customer -c "SELECT 1"
```

### Service not registering with Eureka
1. Check Eureka is running: http://localhost:8761
2. Check service logs for connection errors
3. Verify `eureka.client.serviceUrl.defaultZone` in application.yml

### Config Server not found
1. Ensure Config Server is running on port 8888
2. Test: `curl http://localhost:8888/customer/dev`
3. Check `config.import` in service's application.yml

---

## 📚 Resources

- **Main Documentation**: `README_IMPLEMENTATION_FLOW.md`
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Cloud**: https://spring.io/projects/spring-cloud
- **Axon Framework**: https://docs.axoniq.io/
- **Microservices Patterns**: https://microservices.io/
- **Event Sourcing**: https://martinfowler.com/eaaDev/EventSourcing.html
- **CQRS Pattern**: https://martinfowler.com/bliki/CQRS.html

---

## ✅ Next Steps

- [ ] Read this START_HERE.md (5 min)
- [ ] Follow Quick Start guide (10 min)
- [ ] Read README_IMPLEMENTATION_FLOW.md (1-2 hours)
- [ ] Explore CustomerAggregate.java code
- [ ] Test all API endpoints
- [ ] Check Eureka dashboard
- [ ] Review database schema
- [ ] Try adding a new feature

---

## 💡 Pro Tips

1. **Always start Eureka first** - other services register with it
2. **Wait for each service** to fully start before starting the next
3. **Check Eureka dashboard** to verify all services are registered
4. **Use Postman** for easier API testing
5. **Monitor logs** to understand the event flow
6. **Read the code** in customer-service as a reference for patterns

---

**🎉 You're ready to start! Begin with the Quick Start guide above.**

For detailed implementation flow, read: **README_IMPLEMENTATION_FLOW.md**

---

**Version**: 1.0  
**Last Updated**: March 2, 2026  
**Project**: ITP E-Banking System

