# Food Delivery Application

---

## Introduction

This is a microservices-based Food Delivery Application built using Spring Boot framework. The application allows two types of users - companies and customers. Companies can add their products and menus, while customers can browse menus and place orders.

## Features

- **User Types**:
    - **Company**: Can add products and menus.
    - **Customer**: Can browse menus and place orders.

- **Services**:
    - **Discovery Service**: For service discovery.
    - **API Gateway**: Routes requests to appropriate services.
    - **Security Service**: Manages authentication and authorization.
    - **Restaurant Service**: Manages restaurants, products, and menus.
    - **Order Service**: Handles order management.
    - **Notification Service**: Sends notifications to users.

## Architecture

The application follows a microservices architecture pattern, where each service is independently deployable and scalable. The communication between services is managed via RESTful APIs.

## Technologies Used

- **Spring Boot**: For building microservices.
- **Spring Cloud**: For managing distributed systems.
- **Spring API Gateway**: For routing and filtering requests.
- **Spring Security**: For authentication and authorization.
- **Spring Data**: For data access.
- **Eureka**: For service discovery.
- **RabbitMQ**: For asynchronous communication between services.
- **Run Docker Compose**:
   ```bash
   docker-compose up -d