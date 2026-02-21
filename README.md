# Bookshop REST API

A Spring Boot REST API designed to power an online bookshop platform. The application provides public access to browse books and categories, secure JWT-based authentication, administrative book management, protected cart functionality for authenticated users, and automated CI/CD deployment to a production server.

## Features
### Authentication & Security

- JWT-based authentication
- Secure endpoints for authenticated users
- Public endpoints for visitors
- Role-based access control

### Book Management

- List all books with pagination
- View book details by ID

### Category Management

- List all available book categories
- Categories are used to organize books

### Cart Management

- View all items in the authenticated user's shopping cart
- Add a book to the shopping cart (requires JWT)
- Update item quantity in the cart
- Remove an item from the cart
- Stock validation is enforced when adding or updating items
- All endpoints require user authentication

### Admin

- Add new books to the system (requires JWT and ADMIN role)
- Delete existing books (requires JWT and ADMIN role)
- Endpoints are protected and only accessible by users with ADMIN privileges

### Unit Testing

- Tested BookService for retrieving all books with pagination.
- Verified getting a book by ID and throwing exceptions if not found.
- Checked CartItemService for adding, updating, and deleting cart items.
- Ensured quantity validations in the cart were correctly handled.
- Tested CategoryService for retrieving all categories and handling empty results.
- Used Mockito to mock repositories and focus on service layer logic.

### Integration Testing

- Spring Boot `@SpringBootTest` with H2 in-memory database
- Repository layer tests
- Persist and retrieve entities: Book, Category, User, CartItem
- Transactional tests to ensure rollback after each test
- Validate database operations and queries
- Ensure repository methods behave correctly in a real persistence context

## Project Architecture

The project follows a layered architecture:

com.bookshop

│

├── controller        → REST Controllers (API endpoints)

├── service           → Service interfaces

├── repository        → JPA repositories

├── entity            → Database entities

├── dto               → Data Transfer Objects

├── config            → Security & JWT configuration


## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Hibernate
- MySQL
- H2
- Maven
- JUnit 5
- Mockito

## API Endpoints
### Public Endpoints
Get All Books (Paginated) :

- GET /api/public/books?page=0&size=5

Get Book by ID :

- GET /api/public/books/{id}

Get All Categories

- GET /api/public/categories
### Protected Endpoints (JWT Required)
Add Book
POST /api/admin/books
Update Book
PUT /api/admin/books/{id}
Delete Book
DELETE /api/admin/books/{id}
Cart Operations
POST /api/cart
PUT /api/cart/{id}
DELETE /api/cart/{id}
⚙ Installation & Running the Project
### Clone the repository

**git clone https://github.com/your-username/bookshop.git**

**cd bookshop**

### Build the project

**mvn clean install**

### Run the application
**mvn spring-boot:run**

- Or run directly from your IDE.

### Access the API

Default URL:

**http://localhost:8080**

### JWT Authentication Flow

- Register or login
- Receive JWT token
- Add token in request header:
- Authorization: Bearer your_token_here
- Access protected endpoints

### Running Unit Tests

To run tests:

**mvn test**

The project includes:

- Service layer unit tests
- Pagination tests
- Exception handling tests
- Mocked repository interactions

## Design Decisions

- Constructor injection used for better testability.
- DTO pattern used to avoid exposing entities directly.
- Pagination implemented for performance optimization.
- Repository layer uses Spring Data JPA.
- Exception handling uses RuntimeException (can be improved with @ControllerAdvice).

## Future Improvements

- Implement global exception handler
- Add refresh token mechanism
- Improve error response structure

## Author

- Developed as part of an academic project demonstrating:
- Git workflow with feature branches
