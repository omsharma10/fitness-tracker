package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.service.ActivityLogService;
import com.fittrack.fitness_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final ActivityLogService activityLogService;

    public AdminController(
            UserService userService,
            ActivityLogService activityLogService) {

        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

    @GetMapping("/activity-logs")
    public ResponseEntity<?> getActivityLogs() {

        return ResponseEntity.ok(
                activityLogService.getAllLogs()
        );
    }
}