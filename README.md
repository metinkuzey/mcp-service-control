# MCP Service Control

MCP Service Control is a robust microservice-based Java application designed to monitor and manage applications, implement secure authentication with JWT and refresh tokens, and provide observability through Prometheus and Grafana.

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA + PostgreSQL**
- **Prometheus & Grafana** (metrics and dashboards)
- **Swagger/OpenAPI 3** (API documentation)
- **Docker** (optional for future containerization)

---

## ✅ Key Features

### 🧩 Application Monitoring
- Create, update, delete, and query monitored applications
- Application status and metadata (description, URL, timestamps)

### 🔐 Authentication & Authorization
- User registration and login
- JWT-based token generation and validation
- Refresh token lifecycle management

### 📊 Metrics and Observability
- Prometheus metrics exposed at `/actuator/prometheus`
- Grafana dashboard provisioning from JSON files
- Custom application and system health metrics

### 📘 API Documentation
- Swagger UI: [`/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)
- OpenAPI docs: [`/v3/api-docs`](http://localhost:8080/v3/api-docs)

---

## 🚀 Getting Started

### Requirements
- Java 17+
- PostgreSQL database
- Maven 3.x

### Setup

1. **Configure `application.yml`**
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/mcpdb
       username: youruser
       password: yourpassword
2. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the Application**
   - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   - Prometheus: [http://localhost:9090](http://localhost:9090)
   - Grafana: [http://localhost:3000](http://localhost:3000)

---

## 📂 Project Structure

```
com.metinkuzey.mcp
├── controller
├── model
├── repository
├── security
├── service
├── dto
├── config
└── util
```

---

## 📈 Observability

### Prometheus

Sample `prometheus.yml`:

```yaml
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: 'mcp-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

### Grafana

Provisioned via:
```
grafana/
└── provisioning/
    ├── datasources/prometheus.yaml
    └── dashboards/dashboards.yaml
```

---

## 🧪 Test Users

Sample users for manual testing:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

---

## 📚 Related Article

Learn more about the design, features, and implementation of this project in the Medium article:

👉 [Building MCP Service Control: A Secure, Observable, and Scalable Spring Boot Microservice](https://medium.com/@metinkuzeyapp/building-mcp-service-control-a-secure-observable-and-scalable-spring-boot-microservice-c5fc183d7184)

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.