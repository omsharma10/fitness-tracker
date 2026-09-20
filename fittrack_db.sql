-- ============================================================
-- FitTrack Database Setup
-- Online Fitness Tracking Application
-- ============================================================

CREATE DATABASE IF NOT EXISTS fittrack_db;

USE fittrack_db;

-- ------------------------------------------------------------
-- USERS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    age INT,
    height DOUBLE,
    weight DOUBLE,
    fitness_goal VARCHAR(255),
    created_at DATETIME
);

-- ------------------------------------------------------------
-- WORKOUTS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS workouts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    workout_type VARCHAR(100),
    duration INT,
    intensity VARCHAR(50),
    calories DOUBLE,
    workout_date DATE,
    created_at DATETIME,
    CONSTRAINT fk_workout_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- FITNESS GOALS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fitness_goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    goal_type VARCHAR(100),
    target DOUBLE,
    current_value DOUBLE,
    deadline DATE,
    created_at DATETIME,
    CONSTRAINT fk_goal_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- CHALLENGES
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS challenges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    target DOUBLE,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    created_at DATETIME
);

-- ------------------------------------------------------------
-- CHALLENGE PARTICIPANTS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS challenge_participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    challenge_id BIGINT NOT NULL,
    progress DOUBLE NOT NULL DEFAULT 0.0,
    status VARCHAR(50) NOT NULL DEFAULT 'JOINED',
    joined_at DATETIME,
    CONSTRAINT uk_user_challenge UNIQUE (user_id, challenge_id),
    CONSTRAINT fk_participant_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_participant_challenge
        FOREIGN KEY (challenge_id) REFERENCES challenges(id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- FITNESS CONTENT
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fitness_content (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    submitted_by BIGINT,
    approval_status VARCHAR(50),
    created_at DATETIME,
    CONSTRAINT fk_content_user
        FOREIGN KEY (submitted_by) REFERENCES users(id)
        ON DELETE SET NULL
);

-- ------------------------------------------------------------
-- SYSTEM SETTINGS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS system_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    setting_name VARCHAR(255) NOT NULL UNIQUE,
    setting_value VARCHAR(255)
);

-- ------------------------------------------------------------
-- ACTIVITY LOGS
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS activity_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(255),
    timestamp DATETIME,
    CONSTRAINT fk_activity_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE SET NULL
);

-- ------------------------------------------------------------
-- OPTIONAL SAMPLE ADMIN ACCOUNT
-- ------------------------------------------------------------
-- Do NOT insert a plain-text password here.
-- Register the account through the FitTrack application first,
-- then execute:
--
-- UPDATE users
-- SET role = 'ADMIN'
-- WHERE email = 'admin@fittrack.com';
--
-- BCrypt password encoding is handled by the Java application.

-- ------------------------------------------------------------
-- VERIFICATION QUERIES
-- ------------------------------------------------------------
SHOW TABLES;

SELECT id, name, email, role
FROM users;

SELECT *
FROM challenges;

SELECT *
FROM challenge_participants;

SELECT *
FROM fitness_content;

SELECT *
FROM activity_logs;
