# BooksAPI [![CodeFactor](https://www.codefactor.io/repository/github/zpikaa/books/badge)](https://www.codefactor.io/repository/github/zpikaa/books)

A basic REST API built with Spring Boot for managing authors and books.
This API allows you to manage resources such as authors and books, supporting CRUD operations via RESTful endpoints. Pagination is available for listing all authors and books.

## Technologies
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- PostgreSQL (via Docker)
- Maven

---

## Requirements
- JDK 17 or higher
- Maven 3.x
- Docker & Docker Compose
- Git

---

## Installation / Setup

1. Clone the project:
    ```bash
    git clone https://github.com/zPikaa/books.git
    cd projectname
    ```
2. Start the PostgreSQL database using Docker Compose:
   ```bash
   docker-compose up -d
   ```
   > This will start a PostgreSQL container accessible at `localhost:5432` with password `changemeinprod!`.
3. Configure the Spring Boot application in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=changemeinprod!
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints

### AUTHORS Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /authors | Create a new author |
| GET    | /authors | Get all authors (with pagination) |
| GET    | /authors/{id} | Get a specific author by ID |
| PUT    | /authors/{id} | Full update of an author |
| PATCH  | /authors/{id} | Partial update of an author |
| DELETE | /authors/{id} | Delete an author |

### BOOKS Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT    | /books/{isbn} | Create or fully update a book |
| PATCH  | /books/{isbn} | Partial update of a book |
| GET    | /books | Get all books (with pagination) |
| GET    | /books/{isbn} | Get a specific book by ISBN |
| DELETE | /books/{isbn} | Delete a book |

## Testing
Run automated tests with:
```bash
./mvnw test
```

## License
This project is licensed under the MIT License. See the [LICENSE.md](LICENSE.md) file for details.
