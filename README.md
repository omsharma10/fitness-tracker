````markdown
# 🏋️ FitTrack - Online Fitness Tracking Application

### Full-Stack Fitness Tracking Web Application

FitTrack is a Java-based full-stack fitness management application designed to help users manage workouts, fitness goals, challenges, challenge participation, and fitness-related content through a secure web platform.

The application also provides administrative functionality for managing users, challenges, participants, submitted content, and system activities.

---

## ✨ Features

### 👤 User Features

- 🔐 User registration and login
- 👤 User profile management
- 🏋️ Add, view, edit, and delete workouts
- 🎯 Create and update fitness goals
- 🏆 View and join fitness challenges
- 📈 Track challenge progress and status
- 📝 Submit fitness-related content
- 📚 View approved fitness content

### 👨‍💼 Admin Features

- 👥 Manage registered users
- 🏆 Create, edit, and delete challenges
- 👤 Manage challenge participants
- 📊 Monitor participant progress and status
- 🗑️ Remove participants
- 📝 Approve or reject submitted fitness content
- 📋 View system activity logs

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| ☕ Java | Core programming language |
| 🌱 Spring Boot | Backend application framework |
| 🔐 Spring Security | Authentication and authorization |
| 🧩 Spring Data JPA | Data persistence |
| 🐘 Hibernate | Object-relational mapping |
| 🗄️ MySQL | Relational database |
| 🎨 Thymeleaf | Server-side web interface |
| 🅱️ Bootstrap | UI styling |
| ⚡ JavaScript | Client-side functionality |
| 📦 Maven | Build and dependency management |
| 🔧 Git & GitHub | Version control |

---

## 🏗️ Application Architecture

FitTrack follows a layered application architecture.

```text
                         ┌─────────────────────┐
                         │     Web Browser     │
                         │ HTML / CSS / JS     │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     Controllers     │
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
                         │       MySQL         │
                         │      Database       │
                         └─────────────────────┘
````

### Security Layer

Spring Security protects authentication and authorization across the application.

```text
User / Admin
     │
     ▼
Spring Security
     │
     ├── USER Role
     │
     └── ADMIN Role
```

---

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

### Database Tables

The project contains the following main tables:

* `users`
* `workouts`
* `fitness_goals`
* `challenges`
* `challenge_participants`
* `fitness_content`
* `system_settings`
* `activity_logs`

### Database Setup

1. Install and start MySQL Server.
2. Open MySQL Workbench or the MySQL command line.
3. Create the required database.
4. Execute the provided SQL script:

```text
fittrack_db.sql
```

5. Configure the database connection in:

```text
src/main/resources/application.properties
```

Do not commit real database passwords or other secrets to GitHub.

---

## 🔐 Security

The application uses **Spring Security** for authentication and authorization.

Security functionality includes:

* User authentication
* Role-based access control
* Protected application resources
* Password hashing using BCrypt
* USER and ADMIN access separation
* Secure access to protected functionality

### User Role

Normal users have access to fitness-related functionality such as:

* Workouts
* Fitness goals
* Challenges
* Challenge participation
* Fitness content

### Admin Role

Administrators can access management functionality such as:

* User management
* Challenge management
* Participant management
* Content approval
* Activity monitoring

> ⚠️ Never commit database passwords, API keys, tokens, or other secrets to GitHub.

---

## ⚙️ Getting Started

### Prerequisites

Make sure the following are installed:

* Java JDK
* MySQL
* Git
* Maven (optional because the Maven Wrapper is included)

---

### 1. Clone the Repository

```bash
git clone https://github.com/omsharma10/fitness-tracker.git
cd fitness-tracker
```

---

### 2. Create the Database

Open MySQL and create/configure the required database using:

```text
fittrack_db.sql
```

---

### 3. Configure Database Credentials

Configure your local MySQL credentials in:

```text
src/main/resources/application.properties
```

Use your own local credentials.

Do not upload real passwords to GitHub.

---

### 4. Run the Application

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

### 5. Open the Application

Once the application starts, open:

```text
http://localhost:8080
```

---

## 👤 Authentication

The application supports two roles:

* `USER`
* `ADMIN`

Normal users can register through the application.

To create an administrator account, register a normal account first and then update its role in MySQL:

```sql
USE fittrack_db;

UPDATE users
SET role = 'ADMIN'
WHERE email = 'admin@fittrack.com';
```

Then log in again.

> Passwords created through the application are stored using BCrypt hashing.

---

## 🔌 API Groups

The application provides API/controller functionality for:

| API                           | Purpose                          |
| ----------------------------- | -------------------------------- |
| `/api/users`                  | User registration and management |
| `/api/workouts`               | Workout management               |
| `/api/goals`                  | Fitness goal management          |
| `/api/challenges`             | Challenge management             |
| `/api/challenge-participants` | Challenge participation          |
| `/api/content`                | Fitness content                  |
| `/api/admin`                  | Administrative operations        |

---

## 🌐 Default Port

The application runs on:

```text
http://localhost:8080
```

---


## 📚 Project Documentation

The complete project documentation is available in:

```text
FitTrack_Project_Documentation.docx
```

---

## 🚀 Future Improvements

Possible future improvements include:

* 📊 Advanced fitness analytics
* 📈 Progress visualization
* 🔔 Notifications and reminders
* 📱 Improved mobile responsiveness
* ☁️ Cloud deployment
* 🧪 Expanded automated testing
* 🔐 Additional security improvements

---

## 🎓 Learning Outcomes

Through this project, we gained practical experience with:

* Java backend development
* Spring Boot
* Spring Security
* MVC architecture
* Database management
* Spring Data JPA
* Hibernate
* MySQL
* Thymeleaf
* Maven
* Git and GitHub
* Web application development
* Authentication and authorization

---

## 👨‍💻 Author

### Om Sharma

**B.Tech Computer Science & Engineering — Cybersecurity**

Interests:

`Cybersecurity` • `Networking` • `Java` • `Python` • `Linux` • `Software Development`

---

## ⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐.

<p align="center">

### 🔐 Learn • Build • Secure • Improve

</p>

---

**Course:** B.Tech Computer Science and Engineering
**Academic Year:** 2026–2027

```

### One important thing before you replace it

I would **not blindly keep every claim from your current README**. In particular, the `/api/...` section should only remain if those routes actually exist in your current code. Your repository does contain API-oriented controllers, but the exact endpoint mappings should be verified against the controller annotations before presenting them as guaranteed URLs.

Also, **don't put your MySQL password in `application.properties` and then commit it to GitHub**. Use a local configuration/environment variable.

So yes: **your current README needs cleaning, but your project itself isn't broken because of this.** The issue is primarily that multiple README drafts were merged into one file.
```
