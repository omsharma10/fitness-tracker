package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.FitnessGoal;
import com.fittrack.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FitnessGoalRepository extends JpaRepository<FitnessGoal, Long> {

    List<FitnessGoal> findByUser(User user);

    List<FitnessGoal> findByUserOrderByDeadlineAsc(User user);
}