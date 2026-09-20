# 🏋️ FitTrack

### Full-Stack Fitness Tracking Web Application

FitTrack is a full-stack fitness management application built to help users manage workouts, fitness goals, challenges, and fitness activities through a secure web platform.

The application also includes administrative functionality for managing users, challenges, participants, content, and system activities.

---

## ✨ Features

### 👤 User Features

- 🔐 User registration and login
- 👤 User profile management
- 🏋️ Workout management
- 🎯 Fitness goal management
- 🏆 Fitness challenges
- 📈 Challenge progress tracking
- 📝 Fitness content submission
- 📚 View fitness content

### 👨‍💼 Admin Features

- 👥 Manage registered users
- 🏆 Create and manage challenges
- 👤 Manage challenge participants
- 📊 Monitor participant progress
- 📝 Approve or reject submitted content
- 📋 View system activity

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| ☕ Java | Core programming language |
| 🌱 Spring Boot | Backend framework |
| 🔐 Spring Security | Authentication & authorization |
| 🗄️ MySQL | Database |
| 🧩 Spring Data JPA | Data persistence |
| 🐘 Hibernate | ORM |
| 🎨 Thymeleaf | Server-side UI |
| 🅱️ Bootstrap | UI styling |
| ⚡ JavaScript | Client-side functionality |
| 📦 Maven | Build & dependency management |

---

## 🏗️ Application Architecture

```text
                    ┌─────────────────────┐
                    │     Web Browser     │
                    │ HTML • CSS • JS     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Controllers      │
                    │    Spring Boot      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Services       │
                    │   Business Logic    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Repositories     │
                    │   Spring Data JPA   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        MySQL        │
                    │      Database       │
                    └─────────────────────┘

## 📂 Project Structure

```text
fitness-tracker/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/fittrack/fitness_tracker/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── fittrack_db.sql
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

---

## 🗄️ Database

FitTrack uses **MySQL** for persistent application data.

The database setup script is included in:

```text
fittrack_db.sql
```

The application uses Spring Data JPA and Hibernate for database interaction.

---

## 🔐 Security

The application uses **Spring Security** for authentication and authorization.

Security-related functionality includes:

* User authentication
* Role-based access
* Protected application resources
* Password hashing
* Admin/user access separation

> ⚠️ Never commit database passwords, API keys, tokens, or other secrets to GitHub.

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have installed:

* Java JDK
* MySQL
* Git
* Maven (optional because Maven Wrapper is included)

---

### 1. Clone the repository

```bash
git clone https://github.com/omsharma10/fitness-tracker.git
```

```bash
cd fitness-tracker
```

---

### 2. Create the database

Open MySQL and create the required database.

You can use the provided:

```text
fittrack_db.sql
```

---

### 3. Configure database credentials

Configure your local database credentials using environment variables.

```text
DB_USERNAME
DB_PASSWORD
```

Do not commit real credentials to GitHub.

---

### 4. Run the application

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

### 5. Open the application

Once the application starts, open:

```text
http://localhost:8080
```

---

## 📸 Screenshots

Screenshots of the application will be added here.

### 🔐 Login

> Screenshot coming soon

### 🏠 Dashboard

> Screenshot coming soon

### 🏋️ Workout Management

> Screenshot coming soon

### 🎯 Fitness Goals

> Screenshot coming soon

### 🏆 Challenges

> Screenshot coming soon

### 👨‍💼 Admin Dashboard

> Screenshot coming soon

---

## 🚀 Future Improvements

* 📊 Advanced fitness analytics
* 📈 Progress visualization
* 🔔 Notifications and reminders
* 📱 Improved mobile responsiveness
* ☁️ Cloud deployment
* 🧪 Expanded automated testing
* 🔐 Additional security improvements

---

## 🎯 Learning Outcomes

Through this project, I gained practical experience with:

* Java backend development
* Spring Boot application development
* Spring Security
* REST API concepts
* MVC architecture
* Database management
* JPA & Hibernate
* MySQL
* Server-side rendering with Thymeleaf
* Maven project management
* Git & GitHub

---

## 👨‍💻 Author

### Om Sharma

**B.Tech Computer Science & Engineering — Cybersecurity**

Interested in:

`Cybersecurity` • `Networking` • `Java` • `Python` • `Linux` • `Software Development`

---

## ⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐.

---

<p align="center">

### 🔐 Learn • Build • Secure • Improve

</p>
```

---

