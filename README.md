# 📽️ Movie API

A **Spring Boot REST API** for managing movies with **JWT authentication, file handling, role-based access, Swagger API documentation, Docker support, and pagination.**

---

## ⚡ Features
- **CRUD Operations**: Add, update, delete, and fetch movie details.
- **JWT Authentication**: Secure access with roles (USER & ADMIN).
- **File Handling**: Upload and serve movie posters.
- **Pagination & Sorting**: Get paginated and sorted movie lists.
- **Global Exception Handling**: Proper error responses.
- **Swagger API Docs**: Auto-generated API documentation.
- **Docker Support**: Containerized setup for deployment.

---

## 🛠️ Tech Stack
- **Backend**: Spring Boot, Spring Security, JPA (Hibernate)
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **File Storage**: Local storage for movie posters
- **Documentation**: Swagger OpenAPI

---

## 🏗️ Project Setup
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/movie-api.git
cd movie-api
```
### 2️⃣ Configure Database
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movieapi_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
### 3️⃣ Run the Application
```sh
mvn spring-boot:run
```
Or, using Docker:
```sh
docker-compose up --build
```

---

## 🔑 Authentication & Authorization
- **Register**: `/api/v1/auth/register` (Default role: USER)
- **Login**: `/api/v1/auth/login` (Returns JWT access token & refresh token)
- **Refresh Token**: `/api/v1/auth/refresh-token`
- **Admin Role Required** for adding, updating, and deleting movies.

---

## 🎬 API Endpoints
### 🎥 Movie Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `POST` | `/api/v1/movie` | Add a new movie (with poster upload) | Admin |
| `GET` | `/api/v1/movie/{id}` | Get a movie by ID | Public |
| `GET` | `/api/v1/movies` | Get all movies | Public |
| `PUT` | `/api/v1/movie/{id}` | Update a movie (with poster upload) | Admin |
| `DELETE` | `/api/v1/movie/{id}` | Delete a movie | Admin |
| `GET` | `/api/v1/movies/paged` | Get movies with pagination | Public |

### 🖼️ File Handling
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/file/upload` | Upload a movie poster |
| `GET` | `/file/{filename}` | View a movie poster |

---

## 📜 Swagger Documentation
- API Docs: [Swagger UI](http://localhost:8080/docs/swagger-ui.html)
- OpenAPI JSON: `/docs/api-docs`

---

 
