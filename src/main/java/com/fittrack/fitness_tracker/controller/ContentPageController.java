package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.FitnessContentService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentPageController {

    private final UserService userService;
    private final FitnessContentService contentService;

    public ContentPageController(
            UserService userService,
            FitnessContentService contentService) {
        this.userService = userService;
        this.contentService = contentService;
    }

    @GetMapping("/content")
    public String contentPage(
            Model model,
            org.springframework.security.core.Authentication authentication) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);

        model.addAttribute(
                "approvedContent",
                contentService.getApprovedContent()
        );

        model.addAttribute(
                "myContent",
                contentService.getUserContent(user.getId())
        );

        return "content";
    }
}