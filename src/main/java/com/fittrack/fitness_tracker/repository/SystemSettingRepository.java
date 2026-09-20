package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemSettingRepository
        extends JpaRepository<SystemSetting, Long> {

    Optional<SystemSetting> findBySettingName(String settingName);

    boolean existsBySettingName(String settingName);
}