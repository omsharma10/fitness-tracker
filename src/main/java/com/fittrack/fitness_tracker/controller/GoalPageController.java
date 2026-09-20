package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.FitnessGoalService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalPageController {

    private final UserService userService;
    private final FitnessGoalService fitnessGoalService;

    public GoalPageController(
            UserService userService,
            FitnessGoalService fitnessGoalService) {

        this.userService = userService;
        this.fitnessGoalService = fitnessGoalService;
    }

    @GetMapping("/goals")
    public String goalsPage(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        model.addAttribute("user", user);

        model.addAttribute(
                "goals",
                fitnessGoalService.getUserGoals(user.getId())
        );

        return "goals";
    }
}