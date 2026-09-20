package com.fittrack.fitness_tracker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    private Integer age;

    private Double height;

    private Double weight;

    @Column(name = "fitness_goal")
    private String fitnessGoal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Workout> workouts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<FitnessGoal> fitnessGoals = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ChallengeParticipant> challengeParticipations =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "submittedBy"
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<FitnessContent> submittedContent =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "user"
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ActivityLog> activityLogs =
            new ArrayList<>();


    // ================================
    // ROLE
    // ================================

    public enum Role {
        USER,
        ADMIN
    }


    // ================================
    // CONSTRUCTOR
    // ================================

    public User() {
    }


    // ================================
    // GETTERS AND SETTERS
    // ================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }


    public List<FitnessGoal> getFitnessGoals() {
        return fitnessGoals;
    }

    public void setFitnessGoals(List<FitnessGoal> fitnessGoals) {
        this.fitnessGoals = fitnessGoals;
    }


    public List<ChallengeParticipant> getChallengeParticipations() {
        return challengeParticipations;
    }

    public void setChallengeParticipations(
            List<ChallengeParticipant> challengeParticipations) {

        this.challengeParticipations =
                challengeParticipations;
    }


    public List<FitnessContent> getSubmittedContent() {
        return submittedContent;
    }

    public void setSubmittedContent(
            List<FitnessContent> submittedContent) {

        this.submittedContent =
                submittedContent;
    }


    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(
            List<ActivityLog> activityLogs) {

        this.activityLogs =
                activityLogs;
    }
}