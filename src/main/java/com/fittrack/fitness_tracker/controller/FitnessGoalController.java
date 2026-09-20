package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.FitnessGoal;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.FitnessGoalService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class FitnessGoalController {

    private final FitnessGoalService fitnessGoalService;
    private final UserService userService;

    public FitnessGoalController(
            FitnessGoalService fitnessGoalService,
            UserService userService) {

        this.fitnessGoalService = fitnessGoalService;
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
    // CREATE GOAL
    // =========================

    @PostMapping("/user/{userId}")
    public ResponseEntity<FitnessGoal> createGoal(
            @PathVariable Long userId,
            @RequestBody FitnessGoal goal,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can create for anyone
        // USER can create only for themselves
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                fitnessGoalService.createGoal(
                        userId,
                        goal
                )
        );
    }

    // =========================
    // GET USER GOALS
    // =========================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FitnessGoal>> getUserGoals(
            @PathVariable Long userId,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can view anyone's goals
        // USER can view only their own
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                fitnessGoalService.getUserGoals(userId)
        );
    }

    // =========================
    // GET GOAL BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<FitnessGoal> getGoalById(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            FitnessGoal goal;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                goal =
                        fitnessGoalService.adminGetGoal(id);

            } else {

                goal =
                        fitnessGoalService.getGoalByIdForUser(
                                id,
                                loggedInUser.getId()
                        );
            }

            return ResponseEntity.ok(goal);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // UPDATE GOAL
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<FitnessGoal> updateGoal(
            @PathVariable Long id,
            @RequestBody FitnessGoal goal,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            FitnessGoal updatedGoal;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                updatedGoal =
                        fitnessGoalService.adminUpdateGoal(
                                id,
                                goal
                        );

            } else {

                updatedGoal =
                        fitnessGoalService.updateGoal(
                                id,
                                loggedInUser.getId(),
                                goal
                        );
            }

            return ResponseEntity.ok(updatedGoal);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // UPDATE PROGRESS
    // =========================

    @PutMapping("/{id}/progress")
    public ResponseEntity<FitnessGoal> updateProgress(
            @PathVariable Long id,
            @RequestParam Double currentValue,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            FitnessGoal updatedGoal;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                updatedGoal =
                        fitnessGoalService.adminUpdateProgress(
                                id,
                                currentValue
                        );

            } else {

                updatedGoal =
                        fitnessGoalService.updateProgress(
                                id,
                                loggedInUser.getId(),
                                currentValue
                        );
            }

            return ResponseEntity.ok(updatedGoal);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // DELETE GOAL
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                fitnessGoalService.adminDeleteGoal(id);

            } else {

                fitnessGoalService.deleteGoal(
                        id,
                        loggedInUser.getId()
                );
            }

            return ResponseEntity.ok(
                    "Goal deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(403)
                    .body(e.getMessage());
        }
    }
}