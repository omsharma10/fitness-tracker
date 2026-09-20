package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.ActivityLog;
import com.fittrack.fitness_tracker.service.ActivityLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    // Record an activity
    @PostMapping("/user/{userId}")
    public ResponseEntity<ActivityLog> logActivity(
            @PathVariable Long userId,
            @RequestParam String action) {

        return ResponseEntity.ok(
                activityLogService.logActivity(userId, action)
        );
    }

    // Get all activity logs
    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllLogs() {

        return ResponseEntity.ok(
                activityLogService.getAllLogs()
        );
    }

    // Get activity logs of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLog>> getUserLogs(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                activityLogService.getUserLogs(userId)
        );
    }

    // Delete activity log
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(
            @PathVariable Long id) {

        activityLogService.deleteLog(id);

        return ResponseEntity.ok(
                "Activity log deleted successfully"
        );
    }
}