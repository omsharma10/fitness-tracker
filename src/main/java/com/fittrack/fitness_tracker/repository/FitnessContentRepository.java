package com.fittrack.fitness_tracker.repository;

import com.fittrack.fitness_tracker.entity.FitnessContent;
import com.fittrack.fitness_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FitnessContentRepository
        extends JpaRepository<FitnessContent, Long> {

    List<FitnessContent> findByApprovalStatus(String approvalStatus);

    List<FitnessContent> findBySubmittedBy(User user);

    List<FitnessContent> findByCategory(String category);

    List<FitnessContent> findByApprovalStatusOrderByCreatedAtDesc(
            String approvalStatus
    );
}