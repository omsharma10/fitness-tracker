package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.ChallengeParticipantService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyChallengesPageController {

    private final UserService userService;
    private final ChallengeParticipantService participantService;

    public MyChallengesPageController(
            UserService userService,
            ChallengeParticipantService participantService) {

        this.userService = userService;
        this.participantService = participantService;
    }

    @GetMapping("/my-challenges")
    public String myChallenges(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userService
                .getUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Logged-in user not found")
                );

        model.addAttribute("user", user);

        model.addAttribute(
                "myChallenges",
                participantService.getUserChallenges(user.getId())
        );

        return "my-challenges";
    }
}