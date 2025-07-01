# QuizApp Microservices

A distrubuted quiz application converted from previous monolithic quizapp application built with Spring Boot and Spring Cloud that follows a microservices architechture.

## Architecture Overview

The application consists of the following microservices:

1. **Service Registry (Eureka Server)** - Service discovery server that allows microservices to find and communicate with each other
2. **Question Service** - Manages quiz questions (CRUD operations)
3. **Quiz Service** - Manages quizzes by assembling questions and handling quiz attempts
4. **API Gateway** - Single entry point for all client requests, routing to appropriate microservices

## Technologies Used

- **Java 17** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Cloud** - Microservices toolkit
- **Netflix Eureka** - Service discovery and registration server
- **Spring Cloud Gateway** - API Gateway for routing and filtering requests
- **OpenFeign** - Declarative REST client for service-to-service communication
- **PostgreSQL** - Database for storing questions and quizzes
- **Maven** - Dependency management and build tool
- **Project Lombok** - Reduces boilerplate code

## Service Details

### Service Registry (Eureka Server)
- Port: 8761
- Provides service discovery for all microservices
- Dashboard available at: http://localhost:8761

### Question Service
- Manages quiz questions database
- Provides APIs for:
  - Adding new questions
  - Retrieving questions by category
  - Generating random questions for quizzes
  - Scoring answers

### Quiz Service
- Creates quizzes from questions (via Question Service)
- Provides APIs for:
  - Creating quizzes with specific categories and number of questions
  - Getting quiz questions
  - Submitting answers and getting scores

### API Gateway
- Port: 8765
- Single entry point for clients
- Routes requests to appropriate microservices
- URL pattern: http://localhost:8765/{service-id}/{api-endpoint}

## Database Setup

The application requires two PostgreSQL databases:
- **quizapp** - Used by the Question Service
- **quizdb** - Used by the Quiz Service

Ensure PostgreSQL is running and these databases are created before starting the services.

## How to Run

### Prerequisites
- Java 17 or higher
- PostgreSQL
- Maven

### Steps

1. **Start PostgreSQL and create required databases**

```bash
# Create databases
createdb quizapp
createdb quizdb
```

2. **Start the Service Registry (Eureka Server) first**

```bash
cd service-registry
./mvnw spring-boot:run
```

3. **Start the Question Service**

```bash
cd question-service
./mvnw spring-boot:run
```

4. **Start the Quiz Service**

```bash
cd quiz-service
./mvnw spring-boot:run
```

5. **Start the API Gateway**

```bash
cd api-gateway
./mvnw spring-boot:run
```

## API Endpoints

### Question Service

- `GET /question/allQuestions` - Get all questions
- `GET /question/category/{category}` - Get questions by category
- `POST /question/add` - Add a new question
- `GET /question/generate?category={category}&numQ={numQuestions}` - Generate random questions
- `POST /question/getQuestions` - Get questions by IDs
- `POST /question/getScore` - Calculate score based on answers

### Quiz Service

- `POST /quiz/create` - Create a new quiz
- `GET /quiz/get/{id}` - Get quiz questions by quiz ID
- `POST /quiz/submit` - Submit quiz answers and get score

## Example API Usage

### Creating a new quiz

```
POST http://localhost:8765/quiz-service/quiz/create
Content-Type: application/json

{
  "categoryName": "Java",
  "numQuestions": 5,
  "title": "Java Basics Quiz"
}
```

### Getting quiz questions

```
GET http://localhost:8765/quiz-service/quiz/get/1
```

### Submitting quiz answers

```
POST http://localhost:8765/quiz-service/quiz/submit
Content-Type: application/json

[
  {
    "id": 1,
    "response": "Option A"
  },
  {
    "id": 2,
    "response": "Option B"
  }
]
```

## Project Structure

```
quizapp-microservices/
├── service-registry/     # Eureka Server
├── question-service/     # Manages questions
├── quiz-service/         # Manages quizzes
└── api-gateway/          # API Gateway
```

## Future Enhancements

- Add Authentication and Authorization service
- Implement Circuit Breaker pattern for fault tolerance
- Add a web frontend (React/Angular)
- Deploy with Docker and Kubernetes
- Implement distributed tracing