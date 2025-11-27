 
---

# 📚 GOODREADBOOKS APPLICATION

### A Complete Spring Boot REST API with PostgreSQL, Redis & Docker

This repository contains a **Goodreads-style Books Management Application**, built using **Spring Boot**, **PostgreSQL**, **Redis Caching**, and **Docker Compose**.
It includes complete **CRUD APIs** for **Books, Authors, and Publishers**, along with SQL scripts, Postman testing details, and database debugging commands.

---

## 🚀 Features

* Full REST API with Book, Author, and Publisher modules
* PostgreSQL database with schema & sample SQL
* Redis caching for optimized read operations
* Docker Compose setup for PostgreSQL + Redis
* Complete Postman test-ready endpoints
* SQL commands for debugging & learning
* Clean architecture using Controllers, Services, Repositories

---

# 🏗️ Project Architecture

```
Spring Boot Backend
│
├── Books Controller
├── Authors Controller
├── Publishers Controller
│
├── PostgreSQL Database
├── Redis Cache
│
└── Docker (PostgreSQL + Redis)
```

---

# 🗂️ Database Schema

## **BOOK Table**

| Field       | Type     | Description                |
| ----------- | -------- | -------------------------- |
| id          | INT (PK) | Auto-increment ID          |
| name        | VARCHAR  | Book title                 |
| imageUrl    | VARCHAR  | Image URL/path             |
| publisherId | INT (FK) | References publisher table |

## **AUTHOR Table**

| Field      | Type     |
| ---------- | -------- |
| authorId   | INT (PK) |
| authorName | VARCHAR  |

## **PUBLISHER Table**

| Field         | Type     |
| ------------- | -------- |
| publisherId   | INT (PK) |
| publisherName | VARCHAR  |

## **BOOK_AUTHOR Table (Many-to-Many)**

| Field    | Type |
| -------- | ---- |
| bookId   | INT  |
| authorId | INT  |

---

# 💾 PostgreSQL Setup

### **Database Configuration**

Located in `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/goodreads
spring.datasource.username=user1
spring.datasource.password=pass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **Connect to PostgreSQL**

```
psql -U user1 -d goodreads
```

### **Useful SQL Commands**

```
\dt                   -- list all tables
\d book               -- describe table
SELECT * FROM book;   -- show data
```

### **Sample Inserts**

```sql
INSERT INTO author(authorName) VALUES ('J.K. Rowling');
INSERT INTO publisher(publisherName) VALUES ('Penguin Books');

INSERT INTO book(name, imageUrl, publisherId)
VALUES ('Life of Pi','life.jpg',1);

INSERT INTO book_author(bookId, authorId)
VALUES (1, 1);
```

---

# ⚡ Redis Caching

Used to improve read performance.

### **Caches Used**

* `booksList` → caches GET /books
* `books` → caches GET /books/{id}

### **Spring Cache Annotations**

* `@Cacheable` → fetch from cache
* `@CachePut` → update cache after add/update
* `@CacheEvict` → remove from cache on delete

---

# 🧪 API Documentation (CRUD)

## 📘 **Book APIs**

| Method | Endpoint                     | Description        |
| ------ | ---------------------------- | ------------------ |
| GET    | `/books`                     | Get all books      |
| GET    | `/books/{bookId}`            | Get book by ID     |
| POST   | `/publishers/books`          | Add new book       |
| PUT    | `/publishers/books/{bookId}` | Update book        |
| DELETE | `/books/{bookId}`            | Delete book        |
| GET    | `/books/{bookId}/publisher`  | Get book publisher |
| GET    | `/books/{bookId}/authors`    | Get book authors   |

### Example Body for Creating a Book

```json
{
  "name": "Sample Book",
  "imageUrl": "sample.jpg",
  "publisher": { "publisherId": 1 },
  "authors": [
    { "authorId": 1 },
    { "authorId": 2 }
  ]
}
```

---

## 🧑 **Author APIs**

| Method | Endpoint        |
| ------ | --------------- |
| GET    | `/authors`      |
| GET    | `/authors/{id}` |
| POST   | `/authors`      |
| PUT    | `/authors/{id}` |
| DELETE | `/authors/{id}` |

---

## 🏢 **Publisher APIs**

| Method | Endpoint                    |
| ------ | --------------------------- |
| GET    | `/publishers`               |
| GET    | `/publishers/{publisherId}` |
| POST   | `/publishers`               |
| PUT    | `/publishers/{publisherId}` |
| DELETE | `/publishers/{publisherId}` |

---

# 🐳 Docker Setup

### **Start All Containers**

```
docker-compose up -d
```

### **Stop Containers**

```
docker-compose down
```

### **Connect to PostgreSQL Container**

```
docker exec -it goodreads-postgres psql -U user1 -d goodreads
```

### **Connect to Redis Container**

```
docker exec -it goodreads-redis redis-cli
```

---

# 🧵 Build & Run the Application

### **Using Maven**

```
mvn clean install
mvn spring-boot:run
```

### **Run Packaged JAR**

```
java -jar target/goodreads-0.0.1-SNAPSHOT.jar
```

---
 
