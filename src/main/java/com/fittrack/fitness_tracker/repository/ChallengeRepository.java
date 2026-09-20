package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByStatus(String status);

    List<Challenge> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate date1,
            LocalDate date2
    );

    List<Challenge> findByOrderByStartDateDesc();
}