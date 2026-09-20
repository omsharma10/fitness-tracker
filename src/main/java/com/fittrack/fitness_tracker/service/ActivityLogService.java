package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.ActivityLog;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.repository.ActivityLogRepository;
import com.fittrack.fitness_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    public ActivityLogService(
            ActivityLogRepository activityLogRepository,
            UserRepository userRepository) {

        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    // Record an activity for a user
    public ActivityLog logActivity(Long userId, String action) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ActivityLog log = new ActivityLog();

        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        return activityLogRepository.save(log);
    }

    // Get all activity logs
    public List<ActivityLog> getAllLogs() {
        return activityLogRepository.findAllByOrderByTimestampDesc();
    }

    // Get activity logs of a user
    public List<ActivityLog> getUserLogs(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return activityLogRepository.findByUserOrderByTimestampDesc(user);
    }

    // Delete an activity log
    public void deleteLog(Long id) {

        if (!activityLogRepository.existsById(id)) {
            throw new RuntimeException("Activity log not found");
        }

        activityLogRepository.deleteById(id);
    }
}