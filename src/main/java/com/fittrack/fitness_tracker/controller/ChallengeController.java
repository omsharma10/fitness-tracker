package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.Challenge;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.ChallengeService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final UserService userService;

    public ChallengeController(
            ChallengeService challengeService,
            UserService userService) {

        this.challengeService = challengeService;
        this.userService = userService;
    }

    /*
     * Get all challenges
     */
    @GetMapping
    public ResponseEntity<List<Challenge>> getAllChallenges(
            Authentication authentication) {

        return ResponseEntity.ok(
                challengeService.getAllChallenges()
        );
    }

    /*
     * Get challenge by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallengeById(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                challengeService.getChallengeById(id)
        );
    }

    /*
     * Get active challenges
     */
    @GetMapping("/active")
    public ResponseEntity<List<Challenge>> getActiveChallenges(
            Authentication authentication) {

        return ResponseEntity.ok(
                challengeService.getActiveChallenges()
        );
    }

    /*
     * Get challenges active on a specific date
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Challenge>> getChallengesOnDate(
            @PathVariable String date,
            Authentication authentication) {

        LocalDate localDate = LocalDate.parse(date);

        return ResponseEntity.ok(
                challengeService.getChallengesOnDate(localDate)
        );
    }

    /*
     * Create challenge
     * ADMIN ONLY
     */
    @PostMapping
    public ResponseEntity<?> createChallenge(
            @RequestBody Challenge challenge,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {

            return ResponseEntity
                    .status(403)
                    .body("Only admin can create challenges");
        }

        try {

            Challenge savedChallenge =
                    challengeService.createChallenge(challenge);

            return ResponseEntity.ok(savedChallenge);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * Update challenge
     * ADMIN ONLY
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChallenge(
            @PathVariable Long id,
            @RequestBody Challenge challenge,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {

            return ResponseEntity
                    .status(403)
                    .body("Only admin can update challenges");
        }

        try {

            Challenge updatedChallenge =
                    challengeService.updateChallenge(
                            id,
                            challenge
                    );

            return ResponseEntity.ok(updatedChallenge);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * DELETE CHALLENGE
     * ADMIN ONLY
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChallenge(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {

            return ResponseEntity
                    .status(403)
                    .body("Only admin can delete challenges");
        }

        try {

            challengeService.deleteChallenge(id);

            return ResponseEntity.ok(
                    "Challenge deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * Get logged-in user
     */
    private User getLoggedInUser(
            Authentication authentication) {

        return userService
                .getUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}