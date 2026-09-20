package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.Workout;
import com.fittrack.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(User user);

    List<Workout> findByUserOrderByWorkoutDateDesc(User user);
}