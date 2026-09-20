package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.Challenge;
import com.fittrack.fitness_tracker.entity.ChallengeParticipant;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.repository.ChallengeParticipantRepository;
import com.fittrack.fitness_tracker.repository.ChallengeRepository;
import com.fittrack.fitness_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ActivityLogService activityLogService;

    public ChallengeParticipantService(
            ChallengeParticipantRepository participantRepository,
            UserRepository userRepository,
            ChallengeRepository challengeRepository,
            ActivityLogService activityLogService) {

        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.activityLogService = activityLogService;
    }

    // =========================
    // JOIN CHALLENGE
    // =========================

    public ChallengeParticipant joinChallenge(
            Long userId,
            Long challengeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Challenge challenge =
                challengeRepository.findById(challengeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Challenge not found"
                                ));

        if (participantRepository
                .existsByUserAndChallenge(user, challenge)) {

            throw new RuntimeException(
                    "User has already joined this challenge"
            );
        }

        ChallengeParticipant participant =
                new ChallengeParticipant();

        participant.setUser(user);
        participant.setChallenge(challenge);
        participant.setProgress(0.0);
        participant.setStatus("JOINED");

        ChallengeParticipant savedParticipant =
                participantRepository.save(participant);

        activityLogService.logActivity(
                userId,
                "Joined challenge: "
                        + challenge.getTitle()
        );

        return savedParticipant;
    }

    // =========================
    // GET USER CHALLENGES
    // =========================

    public List<ChallengeParticipant> getUserChallenges(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return participantRepository.findByUser(user);
    }

    // =========================
    // GET CHALLENGE PARTICIPANTS
    // =========================

    public List<ChallengeParticipant>
    getChallengeParticipants(Long challengeId) {

        Challenge challenge =
                challengeRepository.findById(challengeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Challenge not found"
                                ));

        return participantRepository
                .findByChallenge(challenge);
    }

    // =========================
    // GET PARTICIPATION
    // =========================

    public ChallengeParticipant getParticipation(
            Long userId,
            Long challengeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Challenge challenge =
                challengeRepository.findById(challengeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Challenge not found"
                                ));

        return participantRepository
                .findByUserAndChallenge(user, challenge)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Participation not found"
                        ));
    }

    // =========================
    // USER OWNERSHIP CHECK
    // =========================

    public ChallengeParticipant getParticipationForUser(
            Long participationId,
            Long userId) {

        ChallengeParticipant participant =
                participantRepository.findById(participationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found"
                                ));

        if (participant.getUser() == null ||
                !participant.getUser()
                        .getId()
                        .equals(userId)) {

            throw new RuntimeException(
                    "You are not allowed to access this participation"
            );
        }

        return participant;
    }

    // =========================
    // USER UPDATE PROGRESS
    // =========================

    public ChallengeParticipant updateProgress(
            Long participationId,
            Long userId,
            Double progress) {

        ChallengeParticipant participant =
                getParticipationForUser(
                        participationId,
                        userId
                );

        participant.setProgress(progress);

        ChallengeParticipant savedParticipant =
                participantRepository.save(participant);

        activityLogService.logActivity(
                userId,
                "Updated challenge progress"
        );

        return savedParticipant;
    }

    // =========================
    // ADMIN UPDATE ANY PROGRESS
    // =========================

    public ChallengeParticipant adminUpdateProgress(
            Long participationId,
            Double progress) {

        ChallengeParticipant participant =
                participantRepository.findById(
                        participationId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Participation not found"
                        ));

        participant.setProgress(progress);

        ChallengeParticipant savedParticipant =
                participantRepository.save(participant);

        if (participant.getUser() != null) {

            activityLogService.logActivity(
                    participant.getUser().getId(),
                    "Challenge progress updated by admin"
            );
        }

        return savedParticipant;
    }

    // =========================
    // USER UPDATE STATUS
    // =========================

    public ChallengeParticipant updateStatus(
            Long participationId,
            Long userId,
            String status) {

        ChallengeParticipant participant =
                getParticipationForUser(
                        participationId,
                        userId
                );

        participant.setStatus(status);

        ChallengeParticipant savedParticipant =
                participantRepository.save(participant);

        activityLogService.logActivity(
                userId,
                "Updated challenge status to " + status
        );

        return savedParticipant;
    }

    // =========================
    // ADMIN UPDATE ANY STATUS
    // =========================

    public ChallengeParticipant adminUpdateStatus(
            Long participationId,
            String status) {

        ChallengeParticipant participant =
                participantRepository.findById(
                        participationId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Participation not found"
                        ));

        participant.setStatus(status);

        ChallengeParticipant savedParticipant =
                participantRepository.save(participant);

        if (participant.getUser() != null) {

            activityLogService.logActivity(
                    participant.getUser().getId(),
                    "Challenge status updated by admin"
            );
        }

        return savedParticipant;
    }

    // =========================
    // USER LEAVE
    // =========================

    public void leaveChallenge(
            Long participationId,
            Long userId) {

        ChallengeParticipant participant =
                getParticipationForUser(
                        participationId,
                        userId
                );

        participantRepository.deleteById(
                participationId
        );

        activityLogService.logActivity(
                userId,
                "Left challenge"
        );
    }

    // =========================
    // ADMIN REMOVE ANY USER
    // =========================

    public void adminRemoveParticipant(
            Long participationId) {

        ChallengeParticipant participant =
                participantRepository.findById(
                        participationId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Participation not found"
                        ));

        Long userId = null;

        if (participant.getUser() != null) {
            userId = participant.getUser().getId();
        }

        participantRepository.deleteById(
                participationId
        );

        if (userId != null) {

            activityLogService.logActivity(
                    userId,
                    "Removed from challenge by admin"
            );
        }
    }
}