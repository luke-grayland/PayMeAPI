# PayMe API (Spring Boot & MongoDB)

This project is part of my effort to familiarise myself with modern Java practices by getting hands on with Spring Boot. I'm mapping concepts I already know from C#—like DI, async programming, and clean architecture—to their Java equivalents.

**Live Demo/Swagger Documentation (Hosted on AWS using Elastic Beanstalk):**
http://paymeapi.eu-north-1.elasticbeanstalk.com/swagger

---

## ✅ Implemented Functionality

- **RESTful API Endpoints:**
    
    Basic `GET` and `POST` examples implemented using Spring MVC `@RestController`.
    
- **Dependency Injection:**
    
    Using Spring's `@Autowired` annotation and constructor injection for managing dependencies.
    
- **Data Persistence with MongoDB:**
    
    Integrated MongoDB using Spring Data, with a dedicated repository and service layer.
    
- **Logging:**
    
    Implemented structured logging using SLF4J with Logback.

- **Async/Await Equivalent:**

    Implemented asynchronous operations using `CompletableFuture` for non-blocking APIs.

- **Use of Generics:**
    
    Explored Java generics in to enforce Result<T> pattern.

---

## 🛠️ Planned Features

- **Mutual TLS (mTLS):**
    
    Load and attach X.509 certificates to outbound HTTP requests using Java's `SSLContext` and `HttpClient`.
    
- **LINQ Equivalent:**
    
    Utilise Java Streams API for data filtering, mapping, and transformation.
    
- **Caching:**
    
    Integrate in-memory caching using `Caffeine` or `Spring Cache` abstraction.
    
- **Unit Testing:**
    
    Write unit tests using `JUnit 5` (Jupiter) for core testing & `Mockito` for mocking dependencies.

- **Cloud Deployment (Expanding Azure knowledge to AWS):**
    
    Package and deploy the application using AWS Elastic Beanstalk (hosting), AWS Secrets Manager (config secrets), and AWS Cloud Watch (logging).

---

## Topics Still To Explore

Next up will be:

- Functional programming with `Function`, `Predicate`, and other functional interfaces.

