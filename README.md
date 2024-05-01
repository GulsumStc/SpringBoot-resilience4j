# Error Tolerance and Resilience with Resilience4J in Java Applications

This project aims to provide hands-on learning of the Resilience4J library for error tolerance and resilience in Java applications. Designed primarily for microservices architectures and distributed systems, this library helps in taking precautions against errors that may occur in interactions with external resources, thus making the application more robust and reliable.

## Core Dependencies

- Spring Boot: Fundamental application framework.
- Spring Cloud: Used to facilitate inter-service communication in microservices architecture.
- Lombok: A library used to reduce code repetition and write cleaner code in Java.

## Resilience4J Patterns

This project targets the implementation of the following Resilience4J patterns through example methods:

Resilience4J seamlessly integrates with Spring, allowing the application of specific patterns using annotations.

- Circuit Breaker
- Rate Limiter
- Retry
- Bulkhead
- Time Limiter

### Circuit Breaker
Used to manage potential error situations. It temporarily stops the operation when a certain error threshold is exceeded, preventing error propagation.

### Retry
Used to resist network errors. It becomes more resilient to error situations by retrying a specific operation.

### Rate Limiter
Used to limit requests made within a specific time frame. It prevents overloading and maintains service quality.

### Bulkhead
Used to limit parallel operations and prevent overloading of the service. It provides isolation and prevents one service from affecting others.

### Time Limiter
Used to set a maximum execution time for a specific operation. If the operation is not completed within a certain time frame, a timeout error is received.

The purpose of this project is to understand how these patterns are implemented through example methods and their operational logic.
