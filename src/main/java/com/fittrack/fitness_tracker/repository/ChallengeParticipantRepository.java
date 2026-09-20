package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.Challenge;
import com.fittrack.fitness_tracker.entity.ChallengeParticipant;
import com.fittrack.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long> {

    List<ChallengeParticipant> findByUser(User user);

    List<ChallengeParticipant> findByChallenge(Challenge challenge);

    Optional<ChallengeParticipant> findByUserAndChallenge(
            User user,
            Challenge challenge
    );

    boolean existsByUserAndChallenge(
            User user,
            Challenge challenge
    );

    void deleteByChallenge(Challenge challenge);
}