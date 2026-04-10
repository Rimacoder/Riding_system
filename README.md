# Smart Ride Sharing System (Dynamic Ride-Sharing & Carpooling Platform)

This project is a **Spring Boot-based backend system** developed to implement a ride-sharing platform with core functionalities like user management, ride posting, booking, and payment handling.
It is designed in a modular way to simulate real-world backend architecture.

## Current Implementation

###  User Management & Ride Booking

* User Registration & Login (`/api/auth/register`, `/api/auth/login`)
* Password encryption using BCrypt
* Role-based access: `DRIVER`, `PASSENGER`, `ADMIN`
* Driver profile includes vehicle details (model, plate, capacity)
* Drivers can post rides
* Passengers can search and book rides
* Seat count updates automatically during booking (transaction-safe)


### Fare & Payment Module

* Dynamic fare calculation:
  `fare = (baseFare + ratePerKm × distanceKm) × seatsBooked`
* Simulated payment gateway (for development/testing)
* Payment history tracking for users
* Basic ride matching using source, destination, and date


###  Admin Features

* Admin APIs for monitoring:

  * Total users
  * Total rides
  * Total bookings
  * Total payments

###  Testing & Documentation

* Basic Spring Boot test included
* API and setup documentation provided

##  Tech Stack

* Java 17
* Spring Boot 3
* Spring Security + JWT
* Spring Data JPA
* H2 Database (in-memory)
* Maven

## Project Structure

entity/        → User, Ride, Booking, Payment models  
controller/    → REST APIs  
service/       → Business logic  
repository/    → Database access layer  
security/      → JWT & security configuration  
config/        → Application configs  

##  Authentication Flow

1. Register/Login to generate JWT token
2. Use token to access protected APIs


##  How to Run

### Prerequisites

* Java 17+
* Maven

##  Sample API Flow

### Register User

`POST /api/auth/register`

### Login

`POST /api/auth/login`

### Post Ride

`POST /api/rides/driver/{driverId}`

### Search Ride

`GET /api/rides/search`

### Book Ride

`POST /api/bookings`

### Payment

'POST /api/payments/booking/{bookingId}'



