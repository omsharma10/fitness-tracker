package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.UserService;
import com.fittrack.fitness_tracker.service.WorkoutService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkoutPageController {

    private final UserService userService;
    private final WorkoutService workoutService;

    public WorkoutPageController(
            UserService userService,
            WorkoutService workoutService) {

        this.userService = userService;
        this.workoutService = workoutService;
    }

    @GetMapping("/workouts")
    public String workoutsPage(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        model.addAttribute("user", user);

        model.addAttribute(
                "workouts",
                workoutService.getUserWorkouts(user.getId())
        );

        return "workouts";
    }
}