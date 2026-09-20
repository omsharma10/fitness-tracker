package com.fittrack.fitness_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "challenge_participants",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "challenge_id"}
                )
        }
)
public class ChallengeParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ================= USER =================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // ================= CHALLENGE =================

    /*
     * We don't need the complete Challenge object in the
     * participant JSON response.
     *
     * Ignoring it also prevents circular JSON:
     *
     * Participant -> Challenge -> Participants -> ...
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;


    // ================= PROGRESS =================

    @Column(nullable = false)
    private Double progress = 0.0;


    // ================= STATUS =================

    @Column(nullable = false, length = 50)
    private String status = "JOINED";


    // ================= JOINED DATE =================

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;


    // ================= CONSTRUCTOR =================

    public ChallengeParticipant() {
    }


    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public Double getProgress() {
        return progress;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }


    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}