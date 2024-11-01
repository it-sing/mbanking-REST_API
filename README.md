# Mobile Banking REST API

A simple REST API designed to manage core banking operations. This project was developed as part of a Spring Boot course with my instructor. The API allows for efficient management of users, accounts, account types, card types, and transactions. It provides endpoints to create, update, and retrieve banking data, supporting essential financial interactions and services.

## Features

- **User Management:** Create, update, and manage users, with support for password resets and email verification.
- **Roles and Permissions:** Assign roles and permissions for secure access control.
- **Account Management:** Manage user accounts, including balance, limits, and account types.
- **Card Management:** Create and manage cards with different types ( debit, credit) and associated details.
- **Transactions:** Process and track transactions, including transfers and payment history.
- **Security:** Includes authentication, password protection, 
## Installation

1. **Prerequisites:**
    - This project uses PostgreSQL as the database. Make sure you have PostgreSQL installed and running before setting up the project.
    - **Java Development Kit (JDK):** Ensure JDK version 17 (or the required version for your project) is installed.
    - **Gradle:** This project uses Gradle, so no additional installation is required if using the Gradle Wrapper.
## Configuration
   ```plaintext
   EMAIL_USERNAME=your-email@gmail.com
   EMAIL_PASSWORD=your-email-password
   DB_USERNAME=yourusername
   DB_PASSWORD=yourpassword
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=mbanking
   MEDIA_SERVER_PATH=D:\\path in your computer
   MEDIA_BASE_URI=http://localhost:8080/media/.
```
## Thanks

Thank you for taking the time to explore this project! This API was built as a learning experience with the guidance of my instructor, and I hope it can serve as a helpful resource for others delving into Spring Boot and REST API development. I appreciate any feedback, contributions, or suggestions for improvement. Happy coding!
