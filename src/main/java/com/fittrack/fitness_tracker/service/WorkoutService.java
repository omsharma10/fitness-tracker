package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.entity.Workout;
import com.fittrack.fitness_tracker.repository.UserRepository;
import com.fittrack.fitness_tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            UserRepository userRepository,
            ActivityLogService activityLogService) {

        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    // =========================
    // ADD WORKOUT
    // =========================

    public Workout addWorkout(
            Long userId,
            Workout workout) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        workout.setUser(user);

        Workout savedWorkout =
                workoutRepository.save(workout);

        activityLogService.logActivity(
                userId,
                "Added a new workout"
        );

        return savedWorkout;
    }

    // =========================
    // GET USER WORKOUTS
    // =========================

    public List<Workout> getUserWorkouts(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return workoutRepository
                .findByUserOrderByWorkoutDateDesc(user);
    }

    // =========================
    // GET WORKOUT
    // =========================

    public Workout getWorkoutById(Long id) {

        return workoutRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Workout not found"));
    }

    // =========================
    // USER OWNERSHIP CHECK
    // =========================

    public Workout getWorkoutByIdForUser(
            Long id,
            Long userId) {

        Workout workout = getWorkoutById(id);

        if (workout.getUser() == null ||
                !workout.getUser()
                        .getId()
                        .equals(userId)) {

            throw new RuntimeException(
                    "You are not allowed to access this workout"
            );
        }

        return workout;
    }

    // =========================
    // UPDATE WORKOUT
    // =========================

    public Workout updateWorkout(
            Long id,
            Long userId,
            Workout updatedWorkout) {

        Workout existingWorkout =
                getWorkoutByIdForUser(id, userId);

        existingWorkout.setWorkoutType(
                updatedWorkout.getWorkoutType()
        );

        existingWorkout.setDuration(
                updatedWorkout.getDuration()
        );

        existingWorkout.setIntensity(
                updatedWorkout.getIntensity()
        );

        existingWorkout.setCalories(
                updatedWorkout.getCalories()
        );

        existingWorkout.setWorkoutDate(
                updatedWorkout.getWorkoutDate()
        );

        Workout savedWorkout =
                workoutRepository.save(existingWorkout);

        activityLogService.logActivity(
                userId,
                "Updated a workout"
        );

        return savedWorkout;
    }

    // =========================
    // ADMIN: GET ANY WORKOUT
    // =========================

    public Workout adminGetWorkout(Long id) {

        return getWorkoutById(id);
    }

    // =========================
    // ADMIN: UPDATE ANY WORKOUT
    // =========================

    public Workout adminUpdateWorkout(
            Long id,
            Workout updatedWorkout) {

        Workout existingWorkout =
                getWorkoutById(id);

        existingWorkout.setWorkoutType(
                updatedWorkout.getWorkoutType()
        );

        existingWorkout.setDuration(
                updatedWorkout.getDuration()
        );

        existingWorkout.setIntensity(
                updatedWorkout.getIntensity()
        );

        existingWorkout.setCalories(
                updatedWorkout.getCalories()
        );

        existingWorkout.setWorkoutDate(
                updatedWorkout.getWorkoutDate()
        );

        Workout savedWorkout =
                workoutRepository.save(existingWorkout);

        if (existingWorkout.getUser() != null) {

            activityLogService.logActivity(
                    existingWorkout.getUser().getId(),
                    "Workout updated by admin"
            );
        }

        return savedWorkout;
    }

    // =========================
    // DELETE WORKOUT
    // =========================

    public void deleteWorkout(
            Long id,
            Long userId) {

        Workout workout =
                getWorkoutByIdForUser(id, userId);

        workoutRepository.deleteById(id);

        activityLogService.logActivity(
                userId,
                "Deleted a workout"
        );
    }

    // =========================
    // ADMIN: DELETE ANY WORKOUT
    // =========================

    public void adminDeleteWorkout(Long id) {

        Workout workout =
                getWorkoutById(id);

        Long ownerId = null;

        if (workout.getUser() != null) {
            ownerId = workout.getUser().getId();
        }

        workoutRepository.deleteById(id);

        if (ownerId != null) {

            activityLogService.logActivity(
                    ownerId,
                    "Workout deleted by admin"
            );
        }
    }
}