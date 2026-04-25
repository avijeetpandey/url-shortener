# URL Shortener

This is a simple URL shortener service built with Spring Boot.

## Features

* Shortens long URLs into a compact format.
* Redirects short URLs to their original destination.
* Caches frequently accessed URLs using Redis for faster lookups.
* Uses PostgreSQL for persistent storage of URL mappings.

## Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Redis**
* **Maven**
* **Docker**

## Getting Started

### Prerequisites

* Java 17
* Docker

### Running the Application

1.  **Start the database and cache:**
    ```bash
    docker-compose up -d
    ```

2.  **Run the Spring Boot application:**
    ```bash
    ./mvnw spring-boot:run
    ```

## API

### Shorten a URL

*   **URL:** `/api/v1/urls`
*   **Method:** `POST`
*   **Body:**
    ```json
    {
      "originalUrl": "https://www.example.com/a-very-long-url"
    }
    ```
*   **Success Response:**
    *   **Code:** 201 CREATED
    *   **Content:**
        ```json
        {
          "shortUrl": "j_g-b2",
          "originalUrl": "https://www.example.com/a-very-long-url"
        }
        ```

### Redirect to Original URL

*   **URL:** `/{shortUrl}`
*   **Method:** `GET`
*   **Success Response:**
    *   **Code:** 302 FOUND
    *   **Headers:**
        *   `Location`: `https://www.example.com/a-very-long-url`
