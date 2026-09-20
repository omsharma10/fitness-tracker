package com.fittrack.fitness_tracker.controller;

import com.fittrack.fitness_tracker.entity.SystemSetting;
import com.fittrack.fitness_tracker.service.SystemSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingService settingService;

    public SystemSettingController(SystemSettingService settingService) {
        this.settingService = settingService;
    }

    // Create a new setting
    @PostMapping
    public ResponseEntity<SystemSetting> createSetting(
            @RequestBody SystemSetting setting) {

        return ResponseEntity.ok(
                settingService.createSetting(setting)
        );
    }

    // Get all settings
    @GetMapping
    public ResponseEntity<List<SystemSetting>> getAllSettings() {

        return ResponseEntity.ok(
                settingService.getAllSettings()
        );
    }

    // Get setting by ID
    @GetMapping("/{id}")
    public ResponseEntity<SystemSetting> getSettingById(
            @PathVariable Long id) {

        return settingService.getSettingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get setting by name
    @GetMapping("/name/{settingName}")
    public ResponseEntity<SystemSetting> getSettingByName(
            @PathVariable String settingName) {

        return settingService.getSettingByName(settingName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update setting value
    @PutMapping("/{id}")
    public ResponseEntity<SystemSetting> updateSetting(
            @PathVariable Long id,
            @RequestParam String value) {

        return ResponseEntity.ok(
                settingService.updateSetting(id, value)
        );
    }

    // Delete setting
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSetting(
            @PathVariable Long id) {

        settingService.deleteSetting(id);

        return ResponseEntity.ok(
                "Setting deleted successfully"
        );
    }
}