package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.FitnessGoal;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.repository.FitnessGoalRepository;
import com.fittrack.fitness_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessGoalService {

    private final FitnessGoalRepository fitnessGoalRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public FitnessGoalService(
            FitnessGoalRepository fitnessGoalRepository,
            UserRepository userRepository,
            ActivityLogService activityLogService) {

        this.fitnessGoalRepository = fitnessGoalRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    // =========================
    // CREATE GOAL
    // =========================

    public FitnessGoal createGoal(
            Long userId,
            FitnessGoal goal) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        goal.setUser(user);

        FitnessGoal savedGoal =
                fitnessGoalRepository.save(goal);

        activityLogService.logActivity(
                userId,
                "Created a new fitness goal"
        );

        return savedGoal;
    }

    // =========================
    // GET USER GOALS
    // =========================

    public List<FitnessGoal> getUserGoals(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return fitnessGoalRepository
                .findByUserOrderByDeadlineAsc(user);
    }

    // =========================
    // GET GOAL BY ID
    // =========================

    public FitnessGoal getGoalById(Long id) {

        return fitnessGoalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Goal not found"));
    }

    // =========================
    // USER OWNERSHIP CHECK
    // =========================

    public FitnessGoal getGoalByIdForUser(
            Long id,
            Long userId) {

        FitnessGoal goal = getGoalById(id);

        if (goal.getUser() == null ||
                !goal.getUser()
                        .getId()
                        .equals(userId)) {

            throw new RuntimeException(
                    "You are not allowed to access this goal"
            );
        }

        return goal;
    }

    // =========================
    // USER UPDATE
    // =========================

    public FitnessGoal updateGoal(
            Long id,
            Long userId,
            FitnessGoal updatedGoal) {

        FitnessGoal existingGoal =
                getGoalByIdForUser(id, userId);

        existingGoal.setGoalType(
                updatedGoal.getGoalType()
        );

        existingGoal.setTarget(
                updatedGoal.getTarget()
        );

        existingGoal.setCurrentValue(
                updatedGoal.getCurrentValue()
        );

        existingGoal.setDeadline(
                updatedGoal.getDeadline()
        );

        FitnessGoal savedGoal =
                fitnessGoalRepository.save(existingGoal);

        activityLogService.logActivity(
                userId,
                "Updated a fitness goal"
        );

        return savedGoal;
    }

    // =========================
    // USER UPDATE PROGRESS
    // =========================

    public FitnessGoal updateProgress(
            Long id,
            Long userId,
            Double currentValue) {

        FitnessGoal goal =
                getGoalByIdForUser(id, userId);

        goal.setCurrentValue(currentValue);

        FitnessGoal savedGoal =
                fitnessGoalRepository.save(goal);

        activityLogService.logActivity(
                userId,
                "Updated fitness goal progress"
        );

        return savedGoal;
    }

    // =========================
    // ADMIN: GET ANY GOAL
    // =========================

    public FitnessGoal adminGetGoal(Long id) {

        return getGoalById(id);
    }

    // =========================
    // ADMIN: UPDATE ANY GOAL
    // =========================

    public FitnessGoal adminUpdateGoal(
            Long id,
            FitnessGoal updatedGoal) {

        FitnessGoal existingGoal =
                getGoalById(id);

        existingGoal.setGoalType(
                updatedGoal.getGoalType()
        );

        existingGoal.setTarget(
                updatedGoal.getTarget()
        );

        existingGoal.setCurrentValue(
                updatedGoal.getCurrentValue()
        );

        existingGoal.setDeadline(
                updatedGoal.getDeadline()
        );

        FitnessGoal savedGoal =
                fitnessGoalRepository.save(existingGoal);

        if (existingGoal.getUser() != null) {

            activityLogService.logActivity(
                    existingGoal.getUser().getId(),
                    "Fitness goal updated by admin"
            );
        }

        return savedGoal;
    }

    // =========================
    // ADMIN: UPDATE ANY PROGRESS
    // =========================

    public FitnessGoal adminUpdateProgress(
            Long id,
            Double currentValue) {

        FitnessGoal goal =
                getGoalById(id);

        goal.setCurrentValue(currentValue);

        FitnessGoal savedGoal =
                fitnessGoalRepository.save(goal);

        if (goal.getUser() != null) {

            activityLogService.logActivity(
                    goal.getUser().getId(),
                    "Fitness goal progress updated by admin"
            );
        }

        return savedGoal;
    }

    // =========================
    // USER DELETE
    // =========================

    public void deleteGoal(
            Long id,
            Long userId) {

        FitnessGoal goal =
                getGoalByIdForUser(id, userId);

        fitnessGoalRepository.deleteById(id);

        activityLogService.logActivity(
                userId,
                "Deleted a fitness goal"
        );
    }

    // =========================
    // ADMIN: DELETE ANY GOAL
    // =========================

    public void adminDeleteGoal(Long id) {

        FitnessGoal goal =
                getGoalById(id);

        Long ownerId = null;

        if (goal.getUser() != null) {
            ownerId = goal.getUser().getId();
        }

        fitnessGoalRepository.deleteById(id);

        if (ownerId != null) {

            activityLogService.logActivity(
                    ownerId,
                    "Fitness goal deleted by admin"
            );
        }
    }
}