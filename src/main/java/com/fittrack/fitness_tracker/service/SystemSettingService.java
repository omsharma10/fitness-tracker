package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.SystemSetting;
import com.fittrack.fitness_tracker.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemSettingService {

    private final SystemSettingRepository settingRepository;

    public SystemSettingService(SystemSettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    // Create a new setting
    public SystemSetting createSetting(SystemSetting setting) {

        if (settingRepository.existsBySettingName(setting.getSettingName())) {
            throw new RuntimeException("Setting already exists");
        }

        return settingRepository.save(setting);
    }

    // Get all settings
    public List<SystemSetting> getAllSettings() {
        return settingRepository.findAll();
    }

    // Get setting by name
    public Optional<SystemSetting> getSettingByName(String settingName) {
        return settingRepository.findBySettingName(settingName);
    }

    // Get setting by ID
    public Optional<SystemSetting> getSettingById(Long id) {
        return settingRepository.findById(id);
    }

    // Update a setting
    public SystemSetting updateSetting(Long id, String value) {

        SystemSetting setting = settingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        setting.setSettingValue(value);

        return settingRepository.save(setting);
    }

    // Delete a setting
    public void deleteSetting(Long id) {

        if (!settingRepository.existsById(id)) {
            throw new RuntimeException("Setting not found");
        }

        settingRepository.deleteById(id);
    }
}