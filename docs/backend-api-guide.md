
# Green Cart 2.0 – Backend API Guide

## Technology Stack
- Java 21
- Spring Boot 4
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Customer registration |
| POST | /api/auth/register/farmer | Farmer registration |
| POST | /api/auth/login | User login |

## User Profile APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/users/me | View profile |
| PUT | /api/users/me | Update profile |
| PUT | /api/users/me/password | Change password |

## Farmer APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/farmer/me | View farmer profile |
| POST | /api/farmer/products | Add product |
| GET | /api/farmer/products | View own products |
| PUT | /api/farmer/products/{productId} | Update product |
| DELETE | /api/farmer/products/{productId} | Delete product |

## Customer Product APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/customer/products | Browse products |
| GET | /api/customer/products/{productId} | View product details |
| GET | /api/customer/products/search?name=tomato | Search by name |
| GET | /api/customer/products/search?category=Vegetables | Filter by category |

## Security
- JWT-based authentication.
- CUSTOMER, FARMER and ADMIN roles.
- Role-based API authorization.
- BCrypt password hashing.

## Database
Database: green_cart_db

Tables:
- users
- farmers
- products

## API Testing
Implemented APIs tested using Postman.
