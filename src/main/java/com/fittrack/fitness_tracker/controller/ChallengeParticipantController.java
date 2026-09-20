package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.ChallengeParticipant;
import com.fittrack.fitness_tracker.service.ChallengeParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge-participants")
public class ChallengeParticipantController {

    private final ChallengeParticipantService participantService;

    public ChallengeParticipantController(
            ChallengeParticipantService participantService) {
        this.participantService = participantService;
    }

    // ============================================================
    // JOIN CHALLENGE
    // ============================================================

    @PostMapping("/user/{userId}/challenge/{challengeId}")
    public ResponseEntity<?> joinChallenge(
            @PathVariable Long userId,
            @PathVariable Long challengeId) {

        try {
            return ResponseEntity.ok(
                    participantService.joinChallenge(userId, challengeId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // GET USER CHALLENGES
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserChallenges(
            @PathVariable Long userId) {

        try {
            return ResponseEntity.ok(
                    participantService.getUserChallenges(userId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // GET CHALLENGE PARTICIPANTS
    // ============================================================

    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<?> getChallengeParticipants(
            @PathVariable Long challengeId) {

        try {
            return ResponseEntity.ok(
                    participantService.getChallengeParticipants(challengeId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // GET SPECIFIC PARTICIPATION
    // ============================================================

    @GetMapping("/user/{userId}/challenge/{challengeId}")
    public ResponseEntity<?> getParticipation(
            @PathVariable Long userId,
            @PathVariable Long challengeId) {

        try {
            return ResponseEntity.ok(
                    participantService.getParticipation(
                            userId,
                            challengeId
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // GET PARTICIPATION FOR USER
    // ============================================================

    @GetMapping("/{participationId}/user/{userId}")
    public ResponseEntity<?> getParticipationForUser(
            @PathVariable Long participationId,
            @PathVariable Long userId) {

        try {
            return ResponseEntity.ok(
                    participantService.getParticipationForUser(
                            participationId,
                            userId
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // USER - UPDATE PROGRESS
    // ============================================================

    @PutMapping("/{participationId}/user/{userId}/progress")
    public ResponseEntity<?> updateProgress(
            @PathVariable Long participationId,
            @PathVariable Long userId,
            @RequestBody Double progress) {

        try {
            if (progress == null) {
                return ResponseEntity.badRequest()
                        .body("Progress cannot be null");
            }

            if (progress < 0) {
                return ResponseEntity.badRequest()
                        .body("Progress cannot be negative");
            }

            return ResponseEntity.ok(
                    participantService.updateProgress(
                            participationId,
                            userId,
                            progress
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // ADMIN - UPDATE PROGRESS
    //
    // Request body:
    // 15
    //
    // NOT:
    // {"progress":15}
    // ============================================================

    @PutMapping("/{participationId}/progress")
    public ResponseEntity<?> adminUpdateProgress(
            @PathVariable Long participationId,
            @RequestBody Double progress) {

        try {
            if (progress == null) {
                return ResponseEntity.badRequest()
                        .body("Progress cannot be null");
            }

            if (progress < 0) {
                return ResponseEntity.badRequest()
                        .body("Progress cannot be negative");
            }

            return ResponseEntity.ok(
                    participantService.adminUpdateProgress(
                            participationId,
                            progress
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // USER - UPDATE STATUS
    // ============================================================

    @PutMapping("/{participationId}/user/{userId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long participationId,
            @PathVariable Long userId,
            @RequestBody String status) {

        try {
            if (status == null || status.isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Status cannot be empty");
            }

            return ResponseEntity.ok(
                    participantService.updateStatus(
                            participationId,
                            userId,
                            cleanStatus(status)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // ADMIN - UPDATE STATUS
    // ============================================================

    @PutMapping("/{participationId}/status")
    public ResponseEntity<?> adminUpdateStatus(
            @PathVariable Long participationId,
            @RequestBody String status) {

        try {
            if (status == null || status.isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Status cannot be empty");
            }

            return ResponseEntity.ok(
                    participantService.adminUpdateStatus(
                            participationId,
                            cleanStatus(status)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // USER - LEAVE CHALLENGE
    // ============================================================

    @DeleteMapping("/{participationId}/user/{userId}")
    public ResponseEntity<?> leaveChallenge(
            @PathVariable Long participationId,
            @PathVariable Long userId) {

        try {
            participantService.leaveChallenge(
                    participationId,
                    userId
            );

            return ResponseEntity.ok(
                    "Successfully left the challenge"
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // ADMIN - REMOVE PARTICIPANT
    // ============================================================

    @DeleteMapping("/{participationId}")
    public ResponseEntity<?> adminRemoveParticipant(
            @PathVariable Long participationId) {

        try {
            participantService.adminRemoveParticipant(
                    participationId
            );

            return ResponseEntity.ok(
                    "Participant removed successfully"
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ============================================================
    // HELPER
    // ============================================================

    private String cleanStatus(String status) {

        String cleaned = status.trim();

        if (cleaned.startsWith("\"")
                && cleaned.endsWith("\"")
                && cleaned.length() >= 2) {

            cleaned = cleaned.substring(
                    1,
                    cleaned.length() - 1
            );
        }

        return cleaned;
    }
}