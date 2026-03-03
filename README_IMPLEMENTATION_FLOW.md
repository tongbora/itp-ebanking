# ITP E-Banking Project - Implementation Flow Guide
## Project Overview
The **ITP (IT Professional) E-Banking System** is a modern microservices-based architecture built with:
- **Java 21** with Spring Boot 4.0.x
- **Spring Cloud 2025.1.0** (Eureka, Config Server, Gateway)
- **Axon Framework 4.13.0** for Event Sourcing & CQRS
- **PostgreSQL 17.5** for persistence
- **Kafka** for event streaming
- **Docker & Docker Compose** for infrastructure
---
## Architecture Overview
```
┌─────────────────────────────────────────────────────────┐
│                   Client Applications                    │
│     (Admin BFF, Front BFF, Mobile App)                   │
└──────────────────────┬──────────────────────────────────┘
                       │ HTTP/REST
                       ▼
┌─────────────────────────────────────────────────────────┐
│         API Gateway (Spring Cloud Gateway)               │
│     Port 8080 - Routing, Security, Circuit Breaker       │
└──────────────────────┬──────────────────────────────────┘
                       │
     ┌─────────────────┼──────────────────┐
     ▼                 ▼                   ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│Customer      │ │Account       │ │Pipeline      │
│Service       │ │Service       │ │Service       │
│Port 20260    │ │Port 20261    │ │Port 20262    │
│              │ │              │ │              │
│Event         │ │CQRS          │ │Integration   │
│Sourcing      │ │Pattern       │ │Hub           │
└──────┬───────┘ └──────┬───────┘ └──────┬───────┘
       │                │                │
       └────────────────┼────────────────┘
                        │
        ┌───────────────┴────────────────┐
        │                                │
        ▼                                ▼
┌─────────────────────┐        ┌──────────────────┐
│Event Bus (Kafka)    │        │Service Registry  │
│- Event Streaming    │        │(Eureka)          │
│- CDC (Debezium)     │        │Port 8761         │
└─────────────────────┘        └──────────────────┘
        │
        ▼
┌─────────────────────────────────────────────────────────┐
│         PostgreSQL Database Cluster                      │
│ Microservices DB (16851) - IAM DB (16852)               │
└─────────────────────────────────────────────────────────┘
```
---
## Project Structure
```
itp-ebanking/
├── common/                          # Shared domain objects
│   └── src/main/java/com/tongbora/common/
│       ├── CustomerId.java
│       ├── AccountId.java
│       └── CustomerSegmentId.java
│
├── microservices/
│   ├── customer-service/            # Customer management
│   │   ├── domain/
│   │   │   ├── aggregate/
│   │   │   │   └── CustomerAggregate.java
│   │   │   ├── command/
│   │   │   │   ├── CreateCustomerCommand.java
│   │   │   │   └── ChangePhoneNumberCommand.java
│   │   │   ├── event/
│   │   │   │   ├── CustomerCreatedEvent.java
│   │   │   │   └── CustomerPhoneNumberChangedEvent.java
│   │   │   └── valueobject/
│   │   │       ├── CustomerName.java
│   │   │       ├── CustomerEmail.java
│   │   │       └── ... (others)
│   │   ├── application/
│   │   │   ├── CustomerService.java
│   │   │   ├── CustomerServiceImpl.java
│   │   │   ├── CustomerQueryService.java
│   │   │   ├── CustomerQueryServiceImpl.java
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   ├── projection/
│   │   │   └── listener/
│   │   ├── rest/
│   │   │   ├── CustomerController.java
│   │   │   └── CustomerQueryController.java
│   │   └── data/
│   │       ├── entity/
│   │       ├── repository/
│   │       └── mapper/
│   │
│   ├── account-service/             # Account management (similar pattern)
│   └── pipeline-service/            # Integration & orchestration
│
├── spring-cloud/
│   ├── eureka-server/               # Service Discovery (Port 8761)
│   ├── config-server/               # Config Management (Port 8888)
│   ├── gateway-server/              # API Gateway (Port 8080)
│   ├── iam-service/                 # OAuth2 Server (Port 9090)
│   ├── admin-bff/                   # Admin UI Backend
│   └── front-bff/                   # User UI Backend
│
├── config-repo/                     # External configuration
│   ├── application.yml
│   ├── customer/
│   │   ├── customer-dev.yml
│   │   ├── customer-qa.yml
│   │   └── customer-prod.yml
│   └── account/
│       └── ... (similar)
│
└── deployment/                      # Infrastructure as Code
    ├── event-driven-infra/          # Kafka & Debezium
    ├── itp-postgres/                # PostgreSQL
    ├── redis/                       # Redis Cache
    └── vault-server/                # Secrets Management
```
---
## Implementation Flow - Phase by Phase
### PHASE 1: Infrastructure Setup
#### 1.1 Start Infrastructure Services
```bash
# Create Docker network
docker network create itp-net
# Start PostgreSQL
cd deployment/itp-postgres && docker-compose up -d
# Start Redis (optional)
cd deployment/redis && docker-compose up -d
# Start Kafka & Debezium
cd deployment/event-driven-infra && docker-compose up -d
```
#### 1.2 Start Spring Cloud Services
```bash
# Terminal 1: Eureka Server (Service Registry)
cd spring-cloud/eureka-server && ./gradlew bootRun
# Runs on http://localhost:8761
# Terminal 2: Config Server (Configuration Management)
cd spring-cloud/config-server && ./gradlew bootRun
# Runs on http://localhost:8888
# Terminal 3: IAM Service (OAuth2 Authorization)
cd spring-cloud/iam-service && ./gradlew bootRun
# Runs on http://localhost:9090
# Terminal 4: Gateway Server (API Gateway)
cd spring-cloud/gateway-server && ./gradlew bootRun
# Runs on http://localhost:8080
```
---
### PHASE 2: Event Sourcing & CQRS Implementation
#### 2.1 Domain Objects - Command Side (Write)
**Commands** - Represent user intentions:
```java
@Data @Builder
public class CreateCustomerCommand {
    @TargetAggregateIdentifier
    private CustomerId customerId;
    private CustomerName name;
    private CustomerEmail email;
    // ... other fields
}
```
**Events** - Immutable facts about what happened:
```java
@Data @Builder
public class CustomerCreatedEvent {
    private CustomerId customerId;
    private CustomerName name;
    private CustomerEmail email;
    // ... matches command fields
}
```
**Aggregate** - Business logic and state management:
```java
@Aggregate
public class CustomerAggregate {
    @AggregateIdentifier
    private CustomerId customerId;
    // Command Handler - Processes user intention
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand cmd) {
        // Validate business rules
        CustomerCreatedEvent event = CustomerCreatedEvent.builder()
            .customerId(cmd.customerId())
            .name(cmd.name())
            // ... build event
            .build();
        AggregateLifecycle.apply(event);  // Record event
    }
    // Event Handler - Applies event to aggregate state
    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        this.customerId = event.customerId();
        this.name = event.name();
        // ... apply all state changes
    }
}
```
**Flow:**
```
REST Request → CreateCustomerCommand → CommandHandler (Aggregate)
            → CustomerCreatedEvent → Event Store (Database)
            → Kafka Topic (for other services)
```
#### 2.2 Domain Objects - Query Side (Read)
**Projections** - Update read model from events:
```java
@Component
public class CustomerProjection {
    @EventHandler
    public void on(CustomerCreatedEvent event) {
        // Map event to database entity
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerId(event.customerId());
        entity.setName(event.name());
        // ...
        repository.save(entity);
    }
}
```
**Query Handlers** - Respond to queries:
```java
@Component
public class CustomerQueryHandler {
    @QueryHandler
    public Page<CustomerResponse> handle(GetCustomerPageQuery query) {
        // Read from optimized read model (database)
        Pageable pageable = PageRequest.of(
            query.pageNumber, 
            query.pageSize
        );
        Page<CustomerEntity> page = repository.findAll(pageable);
        // Map to response DTO
        return page.map(mapper::toResponse);
    }
}
```
**Flow:**
```
GET Request → GetCustomerPageQuery → QueryHandler
           → Read Database (denormalized)
           → Return Page<CustomerResponse>
```
---
### PHASE 3: Application Services & REST API
#### 3.1 Application Service (Orchestration)
```java
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CommandGateway commandGateway;
    private final CustomerApplicationMapper mapper;
    @Override
    public CreateCustomerResponse createCustomer(
        CreateCustomerRequest request) {
        // Map request to command
        CreateCustomerCommand cmd = mapper.toCommand(
            new CustomerId(UUID.randomUUID()),
            request
        );
        // Send command to Axon framework
        // CommandGateway routes to aggregate handler
        CustomerId result = commandGateway.sendAndWait(cmd);
        return CreateCustomerResponse.builder()
            .customerId(result)
            .message("Customer created successfully")
            .build();
    }
}
```
#### 3.2 REST Controller (Command Side)
```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    @PostMapping
    public CreateCustomerResponse createCustomer(
        @Valid @RequestBody CreateCustomerRequest request) {
        return service.createCustomer(request);
    }
    @PutMapping("/{customerId}/phone-number")
    public ChangePhoneNumberResponse changePhoneNumber(
        @PathVariable UUID customerId,
        @Valid @RequestBody ChangePhoneNumberRequest request) {
        return service.changePhoneNumber(customerId, request);
    }
}
```
#### 3.3 REST Controller (Query Side)
```java
@RestController
@RequestMapping("/api/customers")
public class CustomerQueryController {
    private final QueryGateway queryGateway;
    @GetMapping
    public Page<CustomerResponse> getAllCustomers(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize) {
        GetCustomerPageQuery query = 
            new GetCustomerPageQuery(pageNumber, pageSize);
        return queryGateway.query(query, 
            ResponseTypes.instanceOf(Page.class)
        ).join();
    }
    @GetMapping("/{customerId}/history")
    public List<?> getCustomerHistory(@PathVariable UUID customerId) {
        // Query event store for all events related to customer
        return queryGateway.query(
            new GetCustomerHistoryQuery(customerId),
            ResponseTypes.multipleInstancesOf(Object.class)
        ).join();
    }
}
```
---
### PHASE 4: Persistence Layer
#### 4.1 JPA Entity & Repository
```java
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    private UUID customerId;
    @Embedded
    private CustomerName name;
    @Embedded
    private CustomerEmail email;
    private String phoneNumber;
    // ... other fields
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
@Repository
public interface CustomerRepository 
    extends JpaRepository<CustomerEntity, UUID> {
    Page<CustomerEntity> findAll(Pageable pageable);
}
```
#### 4.2 Database Schema
```sql
CREATE TABLE customers (
    customer_id UUID PRIMARY KEY,
    name_first_name VARCHAR(100),
    name_last_name VARCHAR(100),
    email_value VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    gender VARCHAR(10),
    dob DATE,
    kyc_status VARCHAR(20),
    kyc_approved_date DATE,
    address_street VARCHAR(255),
    address_city VARCHAR(100),
    address_postal_code VARCHAR(20),
    contact_emergency_name VARCHAR(255),
    contact_emergency_phone VARCHAR(20),
    customer_segment_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Index for performance
CREATE INDEX idx_email ON customers(email_value);
CREATE INDEX idx_segment ON customers(customer_segment_id);
-- Enable logical replication for CDC
ALTER SYSTEM SET wal_level = logical;
ALTER SYSTEM SET max_replication_slots = 10;
```
---
### PHASE 5: Configuration Management
#### 5.1 Config Server Central Repository
```yaml
# config-repo/application.yml
service:
  info: IT Professional - Microservice
  tag: itp
  version: 1.0
# config-repo/customer/customer-dev.yml
server:
  port: 20260
spring:
  datasource:
    url: jdbc:postgresql://localhost:16851/db_customer
    username: itpusr
    password: itp@168
# config-repo/customer/customer-prod.yml
server:
  port: 20260
spring:
  datasource:
    url: jdbc:postgresql://prod-db:5432/db_customer
    username: ${DB_USER}
    password: ${DB_PASSWORD}
```
#### 5.2 Service Pulls Configuration
```yaml
# microservices/customer-service/src/main/resources/application.yml
spring:
  application:
    name: customer
  profiles:
    active: dev
  config:
    import: optional:configserver:http://localhost:8888
  cloud:
    config:
      name: customer
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
  instance:
    prefer-ip-address: true
```
**Startup sequence:**
1. Service starts with local `application.yml`
2. Connects to Config Server at `:8888`
3. Requests: `/customer/dev`
4. Receives: `customer-dev.yml`
5. Overrides local properties
---
### PHASE 6: Service Discovery & API Gateway
#### 6.1 Eureka Server
When a service starts:
```java
// customer-service registers automatically
eureka.instance.instance-id = customer
eureka.instance.app-name = customer
eureka.instance.ip-address = localhost
eureka.instance.port = 20260
```
All registered services visible at: http://localhost:8761/eureka/apps
#### 6.2 API Gateway Routing
```yaml
# spring-cloud/gateway-server/src/main/resources/application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: customer-service
          uri: lb://customer  # Load-balanced via Eureka
          predicates:
            - Path=/api/customers/**
          filters:
            - StripPrefix=0
        - id: account-service
          uri: lb://account
          predicates:
            - Path=/api/accounts/**
```
**Request flow:**
```
Client: POST http://gateway:8080/api/customers/...
           ↓ (Gateway checks path)
           ↓ Matches: Path=/api/customers/**
           ↓ Route to: lb://customer
           ↓ (Eureka resolves: customer → localhost:20260)
           ↓
       Customer Service: POST http://localhost:20260/api/customers/...
           ↓
         Response
```
#### 6.3 Security (OAuth2/JWT)
```java
// Gateway security config
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) 
        throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter())
                )
            );
        return http.build();
    }
}
```
Client request:
```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" \
  http://localhost:8080/api/customers
```
Gateway validates token with IAM Service, then routes request.
---
### PHASE 7: Event-Driven Architecture
#### 7.1 Event Publishing to Kafka
When `@CommandHandler` applies an event:
```java
AggregateLifecycle.apply(customerCreatedEvent);
// Axon automatically:
// 1. Saves to Event Store
// 2. Publishes to Kafka topic: events.customer
```
#### 7.2 Change Data Capture (CDC)
PostgreSQL transaction logs are monitored:
```
INSERT INTO customers (...) VALUES (...)
    ↓ (PostgreSQL WAL)
    ↓ (Debezium connector detects)
    ↓
  Kafka topic: db.customers.cdc
    ↓ (Other services listen)
```
#### 7.3 Event Listeners
```java
@Component
public class CustomerEventListener {
    @KafkaListener(topics = "events.customer")
    public void handleCustomerEvent(CustomerEvent event) {
        // React to customer events
        // Update cache, trigger workflows, etc.
    }
    @KafkaListener(topics = "db.customers.cdc")
    public void handleCDCEvent(CDCEvent event) {
        // React to database changes
    }
}
```
---
## Complete Request-Response Flow Example
### Create Customer: Complete Journey
```
1. CLIENT REQUEST
   ═════════════════════════════════════════════
   POST http://localhost:8080/api/customers
   {
     "name": {"firstName": "John", "lastName": "Doe"},
     "email": {"value": "john@example.com"},
     "phoneNumber": "+1234567890",
     ...
   }
2. GATEWAY AUTHENTICATION
   ═════════════════════════════════════════════
   ✓ Validates JWT token
   ✓ Extracts user context
   ✓ Checks authorization
3. GATEWAY ROUTING
   ═════════════════════════════════════════════
   ✓ Matches: Path=/api/customers/**
   ✓ Route ID: customer-service
   ✓ Eureka resolves: customer → localhost:20260
   ✓ Load balancer selects instance
   ✓ Forwards to: POST localhost:20260/api/customers
4. CUSTOMER SERVICE - REST CONTROLLER
   ═════════════════════════════════════════════
   CustomerController.createCustomer()
   ├─ Validates @Valid on request
   ├─ Calls service.createCustomer(request)
   └─ Returns CreateCustomerResponse
5. APPLICATION SERVICE
   ═════════════════════════════════════════════
   CustomerServiceImpl.createCustomer()
   ├─ Mapper: CreateCustomerRequest → CreateCustomerCommand
   ├─ commandGateway.sendAndWait(command)
   └─ Returns CustomerId
6. AXON COMMAND HANDLING
   ═════════════════════════════════════════════
   CustomerAggregate(CreateCustomerCommand)
   ├─ @CommandHandler constructor
   ├─ Business rule validation
   ├─ Creates CustomerCreatedEvent
   └─ AggregateLifecycle.apply(event)
7. EVENT STORE & PUBLICATION
   ═════════════════════════════════════════════
   Event Storage:
   ├─ Axon Event Store saves CustomerCreatedEvent
   │  (Persisted with timestamp and aggregate ID)
   │
   Kafka Publication:
   ├─ Event sent to Kafka topic: events.customer
   ├─ Available for other services to consume
8. EVENT SOURCING
   ═════════════════════════════════════════════
   CustomerAggregate.on(CustomerCreatedEvent)
   ├─ @EventSourcingHandler method
   ├─ this.customerId = event.customerId()
   ├─ this.name = event.name()
   └─ Aggregate state fully reconstructed
9. READ MODEL UPDATE (Async)
   ═════════════════════════════════════════════
   CustomerProjection.on(CustomerCreatedEvent)
   ├─ Listens for event
   ├─ Maps to CustomerEntity
   ├─ Saves to PostgreSQL customers table
   └─ Read model now consistent with event store
10. DATABASE CHANGE
    ═════════════════════════════════════════════
    INSERT INTO customers (customer_id, name, email, ...)
         VALUES ('uuid', 'John', 'john@example.com', ...)
    PostgreSQL WAL (Write-Ahead Log):
    ├─ Records INSERT operation
    ├─ Logical replication enabled
11. CHANGE DATA CAPTURE (CDC)
    ═════════════════════════════════════════════
    Debezium Connector:
    ├─ Monitors PostgreSQL WAL
    ├─ Detects INSERT on customers table
    ├─ Creates CDC event
    └─ Publishes to Kafka: db.customers.cdc
12. OTHER SERVICES REACT
    ═════════════════════════════════════════════
    AccountService, PipelineService, etc.:
    ├─ Listen on events.customer topic
    ├─ React to CustomerCreatedEvent
    ├─ Update their own state/caches
    └─ May trigger further events
13. RESPONSE TO GATEWAY
    ═════════════════════════════════════════════
    CreateCustomerResponse:
    {
      "customerId": "550e8400-e29b-41d4-a716-446655440000",
      "message": "Customer created successfully"
    }
14. GATEWAY RESPONSE TO CLIENT
    ═════════════════════════════════════════════
    HTTP 201 Created
    {
      "customerId": "550e8400-e29b-41d4-a716-446655440000",
      "message": "Customer created successfully"
    }
15. SYSTEM STATE (Eventual Consistency)
    ═════════════════════════════════════════════
    ✓ Event Store: CustomerCreatedEvent recorded
    ✓ Read Model: CustomerEntity in database
    ✓ Kafka: Event available for 7+ days
    ✓ CDC: Change captured and streamed
    ✓ Other services: May have reacted
```
---
## Quick Start Commands
```bash
# Build entire project
./gradlew clean build
# Start Eureka
cd spring-cloud/eureka-server && ./gradlew bootRun
# Start Config Server  
cd spring-cloud/config-server && ./gradlew bootRun
# Start IAM Service
cd spring-cloud/iam-service && ./gradlew bootRun
# Start Gateway
cd spring-cloud/gateway-server && ./gradlew bootRun
# Start Customer Service
cd microservices/customer-service && ./gradlew bootRun
# Test API
curl http://localhost:8080/api/customers
# View Eureka Dashboard
http://localhost:8761
```
---
## Port Reference
| Component | Port | URL |
|-----------|------|-----|
| API Gateway | 8080 | http://localhost:8080 |
| Eureka Server | 8761 | http://localhost:8761 |
| Config Server | 8888 | http://localhost:8888 |
| IAM Service | 9090 | http://localhost:9090 |
| Customer Service | 20260 | http://localhost:20260 |
| Account Service | 20261 | http://localhost:20261 |
| Pipeline Service | 20262 | http://localhost:20262 |
| PostgreSQL (Micro) | 16851 | localhost:16851 |
| PostgreSQL (IAM) | 16852 | localhost:16852 |
---
**Document Version**: 1.0  
**Created**: March 2, 2026
