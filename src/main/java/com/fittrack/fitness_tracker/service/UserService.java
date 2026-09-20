package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.repository.ActivityLogRepository;
import com.fittrack.fitness_tracker.repository.ChallengeParticipantRepository;
import com.fittrack.fitness_tracker.repository.FitnessContentRepository;
import com.fittrack.fitness_tracker.repository.FitnessGoalRepository;
import com.fittrack.fitness_tracker.repository.UserRepository;
import com.fittrack.fitness_tracker.repository.WorkoutRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final FitnessGoalRepository fitnessGoalRepository;
    private final ChallengeParticipantRepository participantRepository;
    private final FitnessContentRepository contentRepository;
    private final ActivityLogRepository activityLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivityLogService activityLogService;

    public UserService(
            UserRepository userRepository,
            WorkoutRepository workoutRepository,
            FitnessGoalRepository fitnessGoalRepository,
            ChallengeParticipantRepository participantRepository,
            FitnessContentRepository contentRepository,
            ActivityLogRepository activityLogRepository,
            PasswordEncoder passwordEncoder,
            ActivityLogService activityLogService) {

        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.fitnessGoalRepository = fitnessGoalRepository;
        this.participantRepository = participantRepository;
        this.contentRepository = contentRepository;
        this.activityLogRepository = activityLogRepository;
        this.passwordEncoder = passwordEncoder;
        this.activityLogService = activityLogService;
    }

    // ================================
    // CREATE USER / REGISTER
    // ================================

    public User createUser(User user) {

        // Validate name
        if (user.getName() == null || user.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        // Validate password
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        // Check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // New users are USER by default
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        // Encrypt password
        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        // Save user to MySQL
        User savedUser = userRepository.save(user);

        // Log registration activity
        try {
            activityLogService.logActivity(
                    savedUser.getId(),
                    "Created a new account"
            );
        } catch (Exception e) {
            // Do not stop registration if activity logging fails
            System.out.println(
                    "Activity log warning: " + e.getMessage()
            );
        }

        return savedUser;
    }


    // ================================
    // GET USER BY ID
    // ================================

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    // ================================
    // GET USER BY EMAIL
    // ================================

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // ================================
    // GET ALL USERS
    // ================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // ================================
    // UPDATE USER
    // ================================

    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setHeight(updatedUser.getHeight());
        existingUser.setWeight(updatedUser.getWeight());
        existingUser.setFitnessGoal(updatedUser.getFitnessGoal());

        User savedUser =
                userRepository.save(existingUser);

        try {
            activityLogService.logActivity(
                    id,
                    "Updated profile"
            );
        } catch (Exception e) {
            System.out.println(
                    "Activity log warning: " + e.getMessage()
            );
        }

        return savedUser;
    }


    // ================================
    // DELETE USER
    // ================================

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Prevent deleting ADMIN
        if (user.getRole() == User.Role.ADMIN) {
            throw new RuntimeException(
                    "Admin account cannot be deleted"
            );
        }

        // Delete activity logs
        activityLogRepository.deleteAll(
                activityLogRepository.findByUser(user)
        );

        // Delete challenge participation
        participantRepository.deleteAll(
                participantRepository.findByUser(user)
        );

        // Delete submitted content
        contentRepository.deleteAll(
                contentRepository.findBySubmittedBy(user)
        );

        // Delete fitness goals
        fitnessGoalRepository.deleteAll(
                fitnessGoalRepository.findByUser(user)
        );

        // Delete workouts
        workoutRepository.deleteAll(
                workoutRepository.findByUser(user)
        );

        // Finally delete user
        userRepository.delete(user);
    }
}