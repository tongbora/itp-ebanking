# 📖 ITP E-Banking Project Documentation Index

## 🎯 Quick Navigation

### For New Developers - Start Here! 👇
**📄 [START_HERE.md](./START_HERE.md)** (460 lines)
- Quick overview of the entire system
- 10-minute quick start guide
- Key concepts explained simply
- Testing examples
- Common troubleshooting tips
- **⏱️ Time to read: 15-20 minutes**

### For Deep Understanding 🔬
**📄 [README_IMPLEMENTATION_FLOW.md](./README_IMPLEMENTATION_FLOW.md)** (672 lines)
- Complete architecture with ASCII diagrams
- 7 implementation phases with full code examples
- Detailed request-response flow (15 steps)
- All design patterns explained in depth
- Database schemas
- Configuration management details
- Event-driven architecture patterns
- **⏱️ Time to read: 1-2 hours**

---

## 📁 Project Documentation Structure

```
itp-ebanking/
├── 🚀 START_HERE.md                      ⭐ BEGIN HERE
│   └─ Quick start + Basic concepts
│
├── 📖 README_IMPLEMENTATION_FLOW.md       ⭐ DEEP DIVE
│   └─ Complete implementation guide
│
├── 📋 README.md (this file)               ⭐ NAVIGATION
│   └─ Documentation index
│
└── Source Code/
    ├── microservices/
    │   ├── customer-service/
    │   ├── account-service/
    │   └── pipeline-service/
    └── spring-cloud/
        ├── eureka-server/
        ├── config-server/
        ├── gateway-server/
        └── iam-service/
```

---

## 🗺️ Learning Path

### Level 1: Beginner (Day 1)
1. ✅ Read **START_HERE.md** (20 minutes)
2. ✅ Follow the Quick Start guide (10 minutes)
3. ✅ Test the API endpoints (10 minutes)
4. ✅ Check Eureka dashboard (5 minutes)

**Goal**: Understand what the system does and see it running.

### Level 2: Intermediate (Day 2-3)
1. ✅ Read **README_IMPLEMENTATION_FLOW.md** sections 1-4 (1 hour)
   - Architecture Overview
   - Event Sourcing & CQRS
   - Application Services & REST API
   - Persistence Layer
2. ✅ Study `CustomerAggregate.java` (30 minutes)
3. ✅ Study `CustomerController.java` (30 minutes)
4. ✅ Trace a request through the logs (30 minutes)

**Goal**: Understand Event Sourcing, CQRS, and the request flow.

### Level 3: Advanced (Week 1)
1. ✅ Read **README_IMPLEMENTATION_FLOW.md** sections 5-7 (1 hour)
   - Configuration Management
   - Service Discovery & API Gateway
   - Event-Driven Architecture
2. ✅ Explore database schema (30 minutes)
3. ✅ Study Gateway routing configuration (30 minutes)
4. ✅ Understand Kafka event publishing (1 hour)

**Goal**: Master the entire architecture and event flow.

### Level 4: Expert (Week 2+)
1. ✅ Implement a new feature (2-4 hours)
2. ✅ Write integration tests (2 hours)
3. ✅ Deploy to Docker containers (1 hour)
4. ✅ Set up monitoring and logging (2 hours)

**Goal**: Become productive with the codebase.

---

## 🎓 What You'll Learn

### Architecture Patterns
- ✅ **Microservices Architecture** - Independent, scalable services
- ✅ **Event Sourcing** - Store all changes as events
- ✅ **CQRS** - Separate read and write models
- ✅ **API Gateway Pattern** - Single entry point
- ✅ **Service Discovery** - Dynamic service registration
- ✅ **Saga Pattern** - Distributed transactions
- ✅ **Event-Driven Architecture** - Async communication

### Technologies
- ✅ **Spring Boot 4.0.x** - Application framework
- ✅ **Spring Cloud** - Microservices infrastructure
- ✅ **Axon Framework** - Event Sourcing & CQRS
- ✅ **PostgreSQL** - Relational database
- ✅ **Apache Kafka** - Event streaming
- ✅ **Debezium** - Change Data Capture (CDC)
- ✅ **Docker** - Containerization
- ✅ **OAuth2/JWT** - Security

---

## 🚀 Quick Commands Reference

### Build & Run
```bash
# Build entire project
./gradlew clean build

# Run specific service
cd microservices/customer-service && ./gradlew bootRun

# Run all tests
./gradlew test
```

### Infrastructure
```bash
# Start databases
docker network create itp-net
cd deployment/itp-postgres && docker-compose up -d

# Stop databases
cd deployment/itp-postgres && docker-compose down
```

### Testing
```bash
# Create customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"name":{"firstName":"John","lastName":"Doe"},...}'

# Get customers
curl http://localhost:8080/api/customers?pageNumber=0&pageSize=10
```

### Monitoring
```bash
# Eureka Dashboard
open http://localhost:8761

# Config Server
curl http://localhost:8888/customer/dev

# Database
PGPASSWORD=itp@168 psql -h localhost -p 16851 -U itpusr -d db_customer
```

---

## 🔌 Service Ports

| Service | Port | URL |
|---------|------|-----|
| Gateway | 8080 | http://localhost:8080 |
| Eureka | 8761 | http://localhost:8761 |
| Config Server | 8888 | http://localhost:8888 |
| IAM Service | 9090 | http://localhost:9090 |
| Customer Service | 20260 | http://localhost:20260 |
| Account Service | 20261 | http://localhost:20261 |
| Pipeline Service | 20262 | http://localhost:20262 |
| PostgreSQL | 16851 | localhost:16851 |

---

## 📊 System Architecture at a Glance

```
┌─────────────────────────────────────────────────┐
│            Client Applications                   │
└────────────────┬────────────────────────────────┘
                 ▼
┌─────────────────────────────────────────────────┐
│      API Gateway (8080)                         │
│      - Authentication                           │
│      - Routing                                  │
│      - Load Balancing                          │
└────────────────┬────────────────────────────────┘
                 ▼
        ┌────────┴────────┐
        ▼                 ▼
┌──────────────┐  ┌──────────────┐
│ Customer     │  │ Account      │
│ Service      │  │ Service      │
│ (20260)      │  │ (20261)      │
│              │  │              │
│ Event        │  │ CQRS         │
│ Sourcing     │  │ Pattern      │
└──────┬───────┘  └──────┬───────┘
       │                 │
       └────────┬────────┘
                ▼
        ┌───────────────┐
        │  Event Bus    │
        │  (Kafka)      │
        └───────────────┘
                ▼
        ┌───────────────┐
        │  PostgreSQL   │
        │  (16851)      │
        └───────────────┘
```

---

## 💡 Key Concepts Summary

### Event Sourcing
Store **all changes** as events, not just current state.
- ✅ Complete audit trail
- ✅ Time travel queries
- ✅ Event replay capability

### CQRS
**Separate** read and write operations.
- ✅ Commands: Modify state
- ✅ Queries: Read optimized views
- ✅ Async synchronization

### Microservices
**Independent** services communicating via APIs and events.
- ✅ Scale independently
- ✅ Deploy independently
- ✅ Technology flexibility

---

## 🛠️ Common Tasks

### Add a New Feature
1. Define Command in `domain/command/`
2. Define Event in `domain/event/`
3. Add `@CommandHandler` in Aggregate
4. Add `@EventSourcingHandler` in Aggregate
5. Update Projection for read model
6. Create REST endpoint

### Debug an Issue
1. Check service logs
2. Check Eureka (are services registered?)
3. Check Config Server (is config correct?)
4. Check database (is data persisted?)
5. Check Kafka (are events published?)

### Deploy Changes
1. Build: `./gradlew build`
2. Test: `./gradlew test`
3. Run: `./gradlew bootRun`
4. Verify in Eureka dashboard
5. Test API endpoints

---

## 📞 Support & Resources

### Documentation
- **START_HERE.md** - Quick start guide
- **README_IMPLEMENTATION_FLOW.md** - Complete guide

### Official Resources
- Spring Boot: https://spring.io/projects/spring-boot
- Spring Cloud: https://spring.io/projects/spring-cloud
- Axon Framework: https://docs.axoniq.io/

### Patterns
- Microservices: https://microservices.io/
- Event Sourcing: https://martinfowler.com/eaaDev/EventSourcing.html
- CQRS: https://martinfowler.com/bliki/CQRS.html

---

## ✅ Getting Started Checklist

- [ ] Read this README.md (5 min)
- [ ] Read START_HERE.md (20 min)
- [ ] Follow Quick Start guide (10 min)
- [ ] Test API endpoints (10 min)
- [ ] Read README_IMPLEMENTATION_FLOW.md Phase 1-4 (1 hour)
- [ ] Study CustomerAggregate.java (30 min)
- [ ] Explore database schema (30 min)
- [ ] Understand event flow (30 min)
- [ ] Try adding a feature (2 hours)

---

## 🎉 Ready to Start?

**👉 Open [START_HERE.md](./START_HERE.md) and begin your journey!**

For questions or issues:
1. Check the documentation files
2. Review the code comments
3. Check Spring Boot and Axon Framework docs

---

**Version**: 1.0  
**Last Updated**: March 2, 2026  
**Project**: ITP E-Banking System  
**Total Documentation Lines**: 1,132+ lines

