package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.entity.Workout;
import com.fittrack.fitness_tracker.service.UserService;
import com.fittrack.fitness_tracker.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;

    public WorkoutController(
            WorkoutService workoutService,
            UserService userService) {

        this.workoutService = workoutService;
        this.userService = userService;
    }

    private User getLoggedInUser(
            Authentication authentication) {

        return userService
                .getUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // =========================
    // ADD WORKOUT
    // ADMIN + USER
    // =========================

    @PostMapping("/user/{userId}")
    public ResponseEntity<Workout> addWorkout(
            @PathVariable Long userId,
            @RequestBody Workout workout,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can add workout for any user
        // USER can add only for themselves
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                workoutService.addWorkout(
                        userId,
                        workout
                )
        );
    }

    // =========================
    // GET USER WORKOUTS
    // =========================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getUserWorkouts(
            @PathVariable Long userId,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can view any user's workouts
        // USER can view only their own
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                workoutService.getUserWorkouts(userId)
        );
    }

    // =========================
    // GET WORKOUT BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            Workout workout;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                workout =
                        workoutService.adminGetWorkout(id);

            } else {

                workout =
                        workoutService.getWorkoutByIdForUser(
                                id,
                                loggedInUser.getId()
                        );
            }

            return ResponseEntity.ok(workout);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // UPDATE WORKOUT
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(
            @PathVariable Long id,
            @RequestBody Workout workout,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            Workout updatedWorkout;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                updatedWorkout =
                        workoutService.adminUpdateWorkout(
                                id,
                                workout
                        );

            } else {

                updatedWorkout =
                        workoutService.updateWorkout(
                                id,
                                loggedInUser.getId(),
                                workout
                        );
            }

            return ResponseEntity.ok(updatedWorkout);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // DELETE WORKOUT
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                workoutService.adminDeleteWorkout(id);

            } else {

                workoutService.deleteWorkout(
                        id,
                        loggedInUser.getId()
                );
            }

            return ResponseEntity.ok(
                    "Workout deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(403)
                    .body(e.getMessage());
        }
    }
}