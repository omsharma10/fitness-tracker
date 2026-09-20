package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.ActivityLog;
import com.fittrack.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository
        extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findByUser(User user);

    List<ActivityLog> findAllByOrderByTimestampDesc();

    List<ActivityLog> findByUserOrderByTimestampDesc(User user);
}