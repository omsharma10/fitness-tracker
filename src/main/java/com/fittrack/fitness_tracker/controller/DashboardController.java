package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.ChallengeParticipantService;
import com.fittrack.fitness_tracker.service.FitnessGoalService;
import com.fittrack.fitness_tracker.service.UserService;
import com.fittrack.fitness_tracker.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserService userService;
    private final WorkoutService workoutService;
    private final FitnessGoalService fitnessGoalService;
    private final ChallengeParticipantService participantService;

    public DashboardController(
            UserService userService,
            WorkoutService workoutService,
            FitnessGoalService fitnessGoalService,
            ChallengeParticipantService participantService) {

        this.userService = userService;
        this.workoutService = workoutService;
        this.fitnessGoalService = fitnessGoalService;
        this.participantService = participantService;
    }


    // =========================================
    // DASHBOARD
    // =========================================

    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            org.springframework.security.core.Authentication authentication) {

        String email = authentication.getName();

        User user = userService
                .getUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        // =========================================
        // GET USER DATA
        // =========================================

        int workoutCount =
                workoutService
                        .getUserWorkouts(user.getId())
                        .size();


        int goalCount =
                fitnessGoalService
                        .getUserGoals(user.getId())
                        .size();


        int challengeCount =
                participantService
                        .getUserChallenges(user.getId())
                        .size();


        int completedChallengeCount =
                (int) participantService
                        .getUserChallenges(user.getId())
                        .stream()
                        .filter(participation ->
                                "COMPLETED".equalsIgnoreCase(
                                        participation.getStatus()
                                ))
                        .count();


        // =========================================
        // SEND DATA TO HTML
        // =========================================

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "workoutCount",
                workoutCount
        );

        model.addAttribute(
                "goalCount",
                goalCount
        );

        model.addAttribute(
                "challengeCount",
                challengeCount
        );

        model.addAttribute(
                "completedChallengeCount",
                completedChallengeCount
        );


        return "dashboard";
    }
}