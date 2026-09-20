# FitTrack - Online Fitness Tracking Application

FitTrack is a Java-based web application for managing fitness activities, workouts, fitness goals, challenges, challenge participation, and fitness-related content.

## Features

### USER
- Register and login securely
- Manage profile
- Add, view, edit and delete workouts
- Create and update fitness goals
- View and join challenges
- Track challenge progress and status
- Submit fitness-related content
- View approved content

### ADMIN
- Manage registered users
- Create, edit and delete challenges
- View and manage challenge participants
- Update participant progress and status
- Remove participants
- Approve or reject fitness content
- View activity logs

## Technology Stack

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Thymeleaf
- Bootstrap
- JavaScript
- Maven
- IntelliJ IDEA
- Postman

## Project Structure

```text
src/main/java/com/fittrack/fitness_tracker/
├── config/
├── controller/
├── entity/
├── repository/
├── security/
└── service/

src/main/resources/
├── templates/
└── application.properties
```

## Database Setup

1. Install and start MySQL Server.
2. Open MySQL Workbench or the MySQL command line.
3. Run the SQL script included with this project:

```text
fittrack_db.sql
```

4. Update the MySQL password in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

## Running the Application

1. Open the project in IntelliJ IDEA.
2. Make sure Java and Maven are configured.
3. Make sure MySQL is running.
4. Verify the database configuration.
5. Run:

```text
FitnessTrackerApplication.java
```

6. Open:

```text
http://localhost:8080
```

## Authentication

The application uses Spring Security.

- Normal accounts use the `USER` role.
- Administrative accounts use the `ADMIN` role.
- Passwords created through the application are stored using BCrypt hashing.

To create an administrator account:

1. Register a normal account.
2. Update its role in MySQL:

```sql
USE fittrack_db;

UPDATE users
SET role = 'ADMIN'
WHERE email = 'admin@fittrack.com';
```

Then log in again.

## API Groups

| API | Purpose |
|---|---|
| `/api/users` | User registration and management |
| `/api/workouts` | Workout management |
| `/api/goals` | Fitness goal management |
| `/api/challenges` | Challenge management |
| `/api/challenge-participants` | Challenge participation |
| `/api/content` | Fitness content |
| `/api/admin` | Administrative operations |

## Database Tables

- `users`
- `workouts`
- `fitness_goals`
- `challenges`
- `challenge_participants`
- `fitness_content`
- `system_settings`
- `activity_logs`

## Default Port

```text
8080
```

## Important Note

If an older database contains manually inserted users with plain-text passwords, those accounts may not authenticate after BCrypt security is enabled. Create a new account through the application or reset the password through the application's registration flow.

## Project Documentation

The complete project report is provided separately as:

```text
FitTrack_Project_Documentation.docx
```

## Author

Name: ______________________________

Roll Number: _______________________

Course: B.Tech Computer Science and Engineering

Academic Year: 2026-2027
