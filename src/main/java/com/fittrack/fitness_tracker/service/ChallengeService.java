package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.Challenge;
import com.fittrack.fitness_tracker.repository.ChallengeParticipantRepository;
import com.fittrack.fitness_tracker.repository.ChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipantRepository participantRepository;
    private final ActivityLogService activityLogService;

    public ChallengeService(
            ChallengeRepository challengeRepository,
            ChallengeParticipantRepository participantRepository,
            ActivityLogService activityLogService) {

        this.challengeRepository = challengeRepository;
        this.participantRepository = participantRepository;
        this.activityLogService = activityLogService;
    }

    public Challenge createChallenge(Challenge challenge) {

        if (challenge.getStatus() == null ||
                challenge.getStatus().isBlank()) {

            challenge.setStatus("ACTIVE");
        }

        return challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findByOrderByStartDateDesc();
    }

    public Challenge getChallengeById(Long id) {

        return challengeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Challenge not found"));
    }

    public List<Challenge> getActiveChallenges() {

        return challengeRepository.findByStatus("ACTIVE");
    }

    public List<Challenge> getChallengesOnDate(LocalDate date) {

        return challengeRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        date,
                        date
                );
    }

    public Challenge updateChallenge(
            Long id,
            Challenge updatedChallenge) {

        Challenge existingChallenge =
                getChallengeById(id);

        existingChallenge.setTitle(
                updatedChallenge.getTitle()
        );

        existingChallenge.setDescription(
                updatedChallenge.getDescription()
        );

        existingChallenge.setTarget(
                updatedChallenge.getTarget()
        );

        existingChallenge.setStartDate(
                updatedChallenge.getStartDate()
        );

        existingChallenge.setEndDate(
                updatedChallenge.getEndDate()
        );

        existingChallenge.setStatus(
                updatedChallenge.getStatus()
        );

        return challengeRepository.save(existingChallenge);
    }

    @Transactional
    public void deleteChallenge(Long id) {

        Challenge challenge = getChallengeById(id);

        String challengeTitle = challenge.getTitle();

        /*
         * First delete all users who joined this challenge.
         * This prevents the foreign-key constraint from
         * blocking challenge deletion.
         */
        participantRepository.deleteByChallenge(challenge);

        /*
         * Now delete the challenge itself.
         */
        challengeRepository.delete(challenge);

        /*
         * Activity log is optional because Challenge itself
         * does not belong to a specific user.
         */
    }
}