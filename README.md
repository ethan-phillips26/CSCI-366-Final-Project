# Online Testing System

A console-based Online Testing System built in Java using PostgreSQL.
The system supports user account management, test creation, test taking,
and score tracking with role-based functionality.

## Team Members
- Ethan Phillips
- Hariom Pughat
- Cedar Longballa
- Ben Kordosky

## Features
- User account creation, update, and deletion
- Login authentication
- Test creation with reusable questions and answers
- Test taking with automatic scoring
- Score tracking
- Role-based access

## Technologies Used
- Java
- PostgreSQL
- JDBC

## Database Setup
1. Install PostgreSQL
2. Create a database named `Final`
3. Run the SQL script in SQL/schema.sql
4. Update the credentials in `DatabaseConnection.java` to your personal information

## How to Run
1. Clone the repository
2. Open in your IDE
3. Run the `Main` class
- Note: If issues running, Install EclipseLink Library. Right-click project -> Properties -> Libraries -> plus sign -> Add Library... -> EclipseLink -> Add Library -> OK
