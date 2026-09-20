package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.FitnessContent;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.service.FitnessContentService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class FitnessContentController {

    private final FitnessContentService contentService;
    private final UserService userService;

    public FitnessContentController(
            FitnessContentService contentService,
            UserService userService) {

        this.contentService = contentService;
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
    // CREATE CONTENT
    // =========================

    @PostMapping("/user/{userId}")
    public ResponseEntity<FitnessContent> createContent(
            @PathVariable Long userId,
            @RequestBody FitnessContent content,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can create for any user
        // USER can create only for themselves
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                contentService.createContent(
                        userId,
                        content
                )
        );
    }

    // =========================
    // GET ALL CONTENT
    // =========================

    @GetMapping
    public ResponseEntity<List<FitnessContent>> getAllContent(
            Authentication authentication) {

        getLoggedInUser(authentication);

        return ResponseEntity.ok(
                contentService.getAllContent()
        );
    }

    // =========================
    // GET CONTENT BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<FitnessContent> getContentById(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            FitnessContent content;

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                content =
                        contentService.getContentById(id);

            } else {

                content =
                        contentService.getContentByIdForUser(
                                id,
                                loggedInUser.getId()
                        );
            }

            return ResponseEntity.ok(content);

        } catch (RuntimeException e) {

            return ResponseEntity.status(403).build();
        }
    }

    // =========================
    // PENDING CONTENT
    // ADMIN = FULL ACCESS
    // USER = AUTHENTICATED VIEW
    // =========================

    @GetMapping("/pending")
    public ResponseEntity<List<FitnessContent>>
    getPendingContent(
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                contentService.getPendingContent()
        );
    }

    // =========================
    // APPROVED CONTENT
    // =========================

    @GetMapping("/approved")
    public ResponseEntity<List<FitnessContent>>
    getApprovedContent(
            Authentication authentication) {

        getLoggedInUser(authentication);

        return ResponseEntity.ok(
                contentService.getApprovedContent()
        );
    }

    // =========================
    // USER CONTENT
    // =========================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FitnessContent>>
    getUserContent(
            @PathVariable Long userId,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        // ADMIN can view anyone's content
        // USER can view only their own
        if (loggedInUser.getRole() != User.Role.ADMIN &&
                !loggedInUser.getId().equals(userId)) {

            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                contentService.getUserContent(userId)
        );
    }

    // =========================
    // CATEGORY
    // =========================

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FitnessContent>>
    getContentByCategory(
            @PathVariable String category,
            Authentication authentication) {

        getLoggedInUser(authentication);

        return ResponseEntity.ok(
                contentService.getContentByCategory(
                        category
                )
        );
    }

    // =========================
    // ADMIN: APPROVE
    // =========================

    @PutMapping("/{id}/approve")
    public ResponseEntity<FitnessContent> approveContent(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                contentService.approveContent(id)
        );
    }

    // =========================
    // ADMIN: REJECT
    // =========================

    @PutMapping("/{id}/reject")
    public ResponseEntity<FitnessContent> rejectContent(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        if (loggedInUser.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                contentService.rejectContent(id)
        );
    }

    // =========================
    // DELETE CONTENT
    // ADMIN = ANY CONTENT
    // USER = OWN CONTENT
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContent(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser =
                getLoggedInUser(authentication);

        try {

            if (loggedInUser.getRole() == User.Role.ADMIN) {

                contentService.adminDeleteContent(id);

            } else {

                contentService.deleteContent(
                        id,
                        loggedInUser.getId()
                );
            }

            return ResponseEntity.ok(
                    "Content deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(403)
                    .body(e.getMessage());
        }
    }
}