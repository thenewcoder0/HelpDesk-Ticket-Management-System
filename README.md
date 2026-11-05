# HelpDesk Ticket Management System

A Java-based console application designed to manage IT helpdesk users and support tickets using MySQL database integration through JDBC.

## Project Overview

The HelpDesk Ticket Management System is a role-based application developed using Java and MySQL.

It provides two user roles:

- **Admin:** Manages user accounts, creates users, and controls account activation and deactivation.
- **Support Engineer:** Creates, views, searches, and updates helpdesk tickets.

The application uses JDBC for database connectivity and BCrypt for secure password hashing.

## Features

### 1. User Management

- User authentication using username and password.
- Secure password hashing using BCrypt.
- First-time password change enforcement.
- Role-based access control (Admin and Support Engineer).
- Create new user accounts.
- Activate and deactivate user accounts.
- Duplicate username and Employee ID validation.

### 2. Ticket Management

- Create new support tickets.
- View all available tickets.
- Search tickets using Ticket ID.
- Update ticket description, status, and priority.
- Automatic Ticket ID generation using MySQL AUTO_INCREMENT.
- Automatic resolution timestamp generation.
- Clear resolution timestamp when a resolved ticket is reopened.
- Maintain ticket records without permanent deletion.

### 3. Validation and Exception Handling

- Validate menu selections and user inputs.
- Prevent empty ticket descriptions.
- Validate ticket status and priority.
- Handle invalid Ticket IDs.
- Handle database connection and SQL exceptions.
- Use PreparedStatement for database operations.

## Technology Stack

Technology						Purpose
Java 17						Application development
JDBC						Database connectivity
MySQL 8.0.46				Relational database
MySQL Connector/J 26.10.0	JDBC driver
jBCrypt 0.4					Password hashing
Eclipse IDE					Development environment
MySQL Workbench				Database management

## Development Concepts

- Object-Oriented Programming (OOP)
- Classes and Objects
- Encapsulation
- Constructors
- ArrayList Collections
- Exception Handling
- JDBC CRUD Operations
- SQL Queries
- Password Hashing

## Database Design

**Database Name:** `HelpDeskDB`

The application uses two main tables.

### 1. Users Table

Stores user account and authentication details.

Column					Data Type						Description
emp_id					VARCHAR(20)						Primary Key
username				VARCHAR(50)						Unique username
password				VARCHAR(255)					BCrypt hashed password
role					VARCHAR(30)						ADMIN / SUPPORT_ENGINEER
account_status			BOOLEAN							Account activation status
password_change_status	BOOLEAN							First-time password change status

### 2. Tickets Table

Stores helpdesk ticket information.

Column				Data Type									Description
ticket_id			INT	Auto-increment 							Primary Key
description			VARCHAR(500)								Ticket description
status				VARCHAR(20)									OPEN / IN_PROGRESS / RESOLVED
priority			VARCHAR(10)									LOW / MEDIUM / HIGH
resolved_at			DATETIME									Resolution timestamp (NULL when unresolved)

## Application Workflow

1. The application establishes a connection with the MySQL database.
2. Existing user accounts and support tickets are loaded from the database.
3. Users can log in using their username and password.
4. The application verifies the password using BCrypt.
5. Account status is checked before granting access.
6. First-time users are required to update their temporary password.
7. Administrators can create users and manage account activation.
8. Support Engineers can create, view, search, and update tickets.
9. Ticket changes are stored in the MySQL database.
10. Users can log out and return to the main menu.

## Installation and Setup

### Prerequisites

Make sure the following tools are installed:

- Java Development Kit (JDK 17)
- Eclipse IDE
- MySQL Server 8.0.46
- MySQL Workbench
- MySQL Connector/J 26.10.0
- jBCrypt 0.4

### Step 1: Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### Step 2: Create the Database

Open MySQL Workbench and execute the following SQL script:

```sql
CREATE DATABASE HelpDeskDB;

USE HelpDeskDB;

CREATE TABLE users (
    emp_id VARCHAR(20) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    account_status BOOLEAN NOT NULL DEFAULT TRUE,
    password_change_status BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    priority VARCHAR(10) NOT NULL DEFAULT 'MEDIUM',
    resolved_at DATETIME NULL
);
```

### Step 3: Configure Database Credentials

Configure the following environment variables in Eclipse Run Configurations:

```text
DB_URL=jdbc:mysql://localhost:3306/HelpDeskDB
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

Replace the sample values with your local MySQL credentials.

**Security:** Never commit actual database credentials to GitHub.

### Step 4: Configure External Libraries

Add these libraries to the Eclipse project:

- MySQL Connector/J
- jBCrypt 0.4

Configure the required module dependencies in `module-info.java`.

### Step 5: Run the Application

1. Import the project into Eclipse.
2. Verify the database configuration.
3. Ensure the required libraries are available.
4. Open `Main.java`.
5. Run the application as a Java Application.

## Testing

The application has undergone manual functional and regression testing.

The following scenarios have been tested:

- Application startup and database connectivity.
- Admin and Support Engineer login.
- User creation and duplicate validation.
- First-time password change.
- BCrypt password verification.
- Account activation and deactivation.
- Ticket creation and database persistence.
- View all tickets.
- Search tickets using valid and invalid Ticket IDs.
- Update ticket description, status, and priority.
- Ticket resolution timestamp generation and clearing.
- Input validation and exception handling.
- Logout and application exit.

## Future Enhancements

- Customer ticket submission.
- Ticket assignment to Support Engineers.
- Ticket comments and activity history.
- Separate change-password functionality.
- Advanced ticket filtering and reporting.
- Ticket escalation and lifecycle management.

## Security Considerations

- Passwords are stored using BCrypt hashing.
- PreparedStatement is used for SQL operations.
- Database credentials are configured through environment variables.
- User account status is verified during authentication.

---

**Project:** HelpDesk Ticket Management System  
**Developer:** Hariharasudhan  
**Language:** Java  
**Database:** MySQL