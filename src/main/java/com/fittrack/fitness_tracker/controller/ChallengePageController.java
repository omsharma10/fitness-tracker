package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.Challenge;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.ChallengeParticipantService;
import com.fittrack.fitness_tracker.service.ChallengeService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChallengePageController {

    private final UserService userService;
    private final ChallengeService challengeService;
    private final ChallengeParticipantService participantService;

    public ChallengePageController(
            UserService userService,
            ChallengeService challengeService,
            ChallengeParticipantService participantService) {

        this.userService = userService;
        this.challengeService = challengeService;
        this.participantService = participantService;
    }

    @GetMapping("/challenges")
    public String challengesPage(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Challenge> challenges =
                challengeService.getAllChallenges();

        model.addAttribute("user", user);
        model.addAttribute("challenges", challenges);

        return "challenges";
    }
}