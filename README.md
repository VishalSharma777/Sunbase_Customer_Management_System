# CMS

This project is a full-stack web application for managing customer data. It provides CRUD operations (Create, Read, Update, Delete) for customer records and includes JWT-based authentication for secure access. The application is built with the following technologies:

- **Backend**: Spring Boot (Java) with Spring Security for authentication
- **Frontend**: React.js with HTML/CSS/JavaScript
- **Database**: MySQL

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)

## Features

- **User Authentication**: JWT-based authentication for secure login and signup.
- **CRUD Operations**: Create, read, update, and delete customer records.
- **Pagination, Sorting, and Searching**: Easily manage large datasets.
- **Responsive Design**: Frontend built with React.js for a smooth user experience.



## Project Structure
customer-management-system/

├── backend/

│ ├── src/

│ │ ├── main/

│ │ │ ├── java/com/example/demo/

│ │ │ │ ├── controller/

│ │ │ │ │ └── CustomerController.java

│ │ │ │ ├── dto/

│ │ │ │ │ ├── LoginResponse.java

│ │ │ │ │ ├── LoginUserDto.java

│ │ │ │ │ └── RegisterUserDto.java

│ │ │ │ ├── exception/

│ │ │ │ │ └── GlobalExceptionHandler.java

│ │ │ │ ├── model/

│ │ │ │ │ ├── CustomerModel.java

│ │ │ │ │ └── User.java

│ │ │ │ ├── repository/

│ │ │ │ │ └── CustomerRepository.java

│ │ │ │ ├── security/

│ │ │ │ │ └── ApplicationConfiguration.java

│ │ │ │ ├── service/

│ │ │ │ │ └── CustomerService.java

│ │ │ ├── resources/

│ │ │ │ └── application.properties

│ └── pom.xml

├── frontend/

│ ├── src/

│ │ ├── components/

│ │ │ ├── Login.js

│ │ │ └── Signup.js

│ │ ├── App.js

│ │ └── index.js

│ └── package.json

└── README.md


## Requirements

**Backend:**

- Java 17 or later
- Maven 3.6.0 or later
- MySQL 8.0 or later

**Frontend:**

- Node.js 16 or later
- npm 7 or later
- React.js

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/customer-management-system.git
    cd customer-management-system/backend
    ```
2. **Set up the MySQL database:**
   - Create a database named `customer_db`.
   - Update the `src/main/resources/application.properties` file with your MySQL credentials.

3. **Build the project with Maven:**

    ```bash
    mvn clean install
    ```

## Configuration

### Application Properties

Update `src/main/resources/application.properties` with the following settings:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


## Running the Project

### Backend

1. **Navigate to the backend directory:**

    ```bash
    cd backend
    ```

2. **Run the backend server:**

    ```bash
    mvn spring-boot:run
    ```

3. The backend server will run on [http://localhost:8072](http://localhost:8072).

### Frontend

1. **Navigate to the frontend directory:**

    ```bash
    cd ../frontend
    ```

2. **Install dependencies and start the frontend server:**

    ```bash
    npm install
    npm start
    ```

3. The frontend will run on [http://localhost:3000](http://localhost:3000).

## API Endpoints

### Authentication

- **POST `/api/v1/authenticate`**  
  **Description:** Authenticate a user and return a JWT token.

### Customers

- **POST `/api/v1/customers/create`**  
  **Description:** Create a new customer.

- **GET `/api/v1/customers/{id}`**  
  **Description:** Get a customer by ID.

- **GET `/api/v1/customers`**  
  **Description:** Get a list of customers with pagination, sorting, and searching.

- **PUT `/api/v1/customers/{id}`**  
  **Description:** Update an existing customer.

- **DELETE `/api/v1/customers/{id}`**  
  **Description:** Delete a customer by ID.
