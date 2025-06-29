# PayMe API (Spring Boot & MongoDB)

This Java/Spring Boot REST API offers functionality to issue and manage payment cards along with their associated KYC-verified user profiles. Includes capabilities to: create, view, and update cards; retrieve card transactions; store, access, and analyze spending data; trigger automatic card blocking using SafeBet mechanism.

SafeBet evaluates spend data from the past 24 hours to decide if a card should be temporarily blocked, based on total spend, frequency, and individual transaction size relative to average monthly income.

**Live Demo/Swagger Documentation (Hosted on AWS using Elastic Beanstalk):**

https://www.paymeapi.online/swagger

---

## ✅ Implemented Functionality:

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

- **Cloud Deployment (Expanding Azure knowledge to AWS):**

  Package and deploy the application using AWS Elastic Beanstalk (hosting), AWS Secrets Manager (config secrets), and AWS Cloud Watch (logging).

- **Streams API:**

  Utilise Java Streams API for data filtering, mapping, and transformation.

- **Lambda Expressions:**
  
  Functional programming style lambda expressions with `Function`, `Predicate`, and other functional interfaces.

- **Other:**

  - Global exception handling using @RestControllerAdvice
  - Inbound request logging using OncePerRequestFilter (AbstractRequestLoggingFilter extends OncePerRequestFilter)

---
## 💡 Design Patterns Used:
   
  - Builder (see ProvisionedCard.java)
  - Composition & Forwarding (see SpendNotificationService.java)
  - Result (see Result.java) 
  - Chain of Responsibility (see SpendProcessor.java)
  - Strategy Enum (see TransactionType.java)
  - Singleton (see CardNumberGenerator.java)

---
## 🛠️ Up Next:

- **Mutual TLS (mTLS):**
    
    Load and attach X.509 certificates to outbound HTTP requests using Java's `SSLContext` and `HttpClient`.
    
- **Caching:**
    
    Integrate in-memory caching using `Caffeine` or `Spring Cache` abstraction.
    
- **Unit Testing:**
    
    Write unit tests using `JUnit 5` (Jupiter) for core testing & `Mockito` for mocking dependencies.


