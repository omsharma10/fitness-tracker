package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.service.ActivityLogService;
import com.fittrack.fitness_tracker.service.ChallengeService;
import com.fittrack.fitness_tracker.service.FitnessContentService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    private final UserService userService;
    private final FitnessContentService contentService;
    private final ChallengeService challengeService;
    private final ActivityLogService activityLogService;

    public AdminPageController(
            UserService userService,
            FitnessContentService contentService,
            ChallengeService challengeService,
            ActivityLogService activityLogService) {

        this.userService = userService;
        this.contentService = contentService;
        this.challengeService = challengeService;
        this.activityLogService = activityLogService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "pendingContent",
                contentService.getPendingContent()
        );

        model.addAttribute(
                "challenges",
                challengeService.getAllChallenges()
        );

        model.addAttribute(
                "activityLogs",
                activityLogService.getAllLogs()
        );

        return "admin";
    }
}