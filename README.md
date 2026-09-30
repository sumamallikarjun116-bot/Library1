# Library Management System — JDBC

A beginner-friendly Java web application using:
- Java 17
- Jakarta Servlet
- JDBC
- MySQL
- Maven
- HTML/CSS

## Features
- Add books
- Search books
- Delete books
- Add members
- Delete members
- Issue books
- Return books
- Availability tracking
- JDBC transactions for issue/return
- PreparedStatement for database queries

## Requirements
1. JDK 17+
2. MySQL 8+
3. Maven
4. Apache Tomcat 11+

## Setup
1. Run `database/library_db.sql` in MySQL.
2. Open `src/main/java/com/library/util/DBConnection.java`.
3. Change:
   `PASSWORD = "your_mysql_password";`
   to your MySQL password.
4. In the project folder run:
   `mvn clean package`
5. Deploy `target/library-management-jdbc.war` to Tomcat 11.
6. Open:
   `http://localhost:8080/library-management-jdbc/library`

## JDBC learning points
The project demonstrates:
- DriverManager
- Connection
- PreparedStatement
- ResultSet
- executeQuery()
- executeUpdate()
- transactions
- commit()
- rollback()
- DAO pattern

## Architecture
Browser -> Servlet -> DAO -> JDBC -> MySQL
