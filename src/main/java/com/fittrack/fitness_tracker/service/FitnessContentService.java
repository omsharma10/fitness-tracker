package com.fittrack.fitness_tracker.service;

import com.fittrack.fitness_tracker.entity.FitnessContent;
import com.fittrack.fitness_tracker.entity.User;
import com.fittrack.fitness_tracker.repository.FitnessContentRepository;
import com.fittrack.fitness_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessContentService {

    private final FitnessContentRepository contentRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public FitnessContentService(
            FitnessContentRepository contentRepository,
            UserRepository userRepository,
            ActivityLogService activityLogService) {

        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    // =========================
    // CREATE CONTENT
    // =========================

    public FitnessContent createContent(
            Long userId,
            FitnessContent content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        content.setSubmittedBy(user);
        content.setApprovalStatus("PENDING");

        FitnessContent savedContent =
                contentRepository.save(content);

        activityLogService.logActivity(
                userId,
                "Submitted fitness content: "
                        + content.getTitle()
        );

        return savedContent;
    }

    // =========================
    // GET ALL CONTENT
    // =========================

    public List<FitnessContent> getAllContent() {
        return contentRepository.findAll();
    }

    // =========================
    // GET CONTENT BY ID
    // =========================

    public FitnessContent getContentById(Long id) {

        return contentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Content not found"
                        ));
    }

    // =========================
    // USER OWNERSHIP CHECK
    // =========================

    public FitnessContent getContentByIdForUser(
            Long id,
            Long userId) {

        FitnessContent content =
                getContentById(id);

        if (content.getSubmittedBy() == null ||
                !content.getSubmittedBy()
                        .getId()
                        .equals(userId)) {

            throw new RuntimeException(
                    "You are not allowed to access this content"
            );
        }

        return content;
    }

    // =========================
    // PENDING CONTENT
    // =========================

    public List<FitnessContent> getPendingContent() {
        return contentRepository
                .findByApprovalStatus("PENDING");
    }

    // =========================
    // APPROVED CONTENT
    // =========================

    public List<FitnessContent> getApprovedContent() {
        return contentRepository
                .findByApprovalStatusOrderByCreatedAtDesc(
                        "APPROVED"
                );
    }

    // =========================
    // USER CONTENT
    // =========================

    public List<FitnessContent> getUserContent(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return contentRepository.findBySubmittedBy(user);
    }

    // =========================
    // CATEGORY
    // =========================

    public List<FitnessContent> getContentByCategory(
            String category) {

        return contentRepository.findByCategory(category);
    }

    // =========================
    // ADMIN: APPROVE
    // =========================

    public FitnessContent approveContent(Long id) {

        FitnessContent content =
                getContentById(id);

        content.setApprovalStatus("APPROVED");

        FitnessContent savedContent =
                contentRepository.save(content);

        if (content.getSubmittedBy() != null) {

            activityLogService.logActivity(
                    content.getSubmittedBy().getId(),
                    "Content approved: "
                            + content.getTitle()
            );
        }

        return savedContent;
    }

    // =========================
    // ADMIN: REJECT
    // =========================

    public FitnessContent rejectContent(Long id) {

        FitnessContent content =
                getContentById(id);

        content.setApprovalStatus("REJECTED");

        FitnessContent savedContent =
                contentRepository.save(content);

        if (content.getSubmittedBy() != null) {

            activityLogService.logActivity(
                    content.getSubmittedBy().getId(),
                    "Content rejected: "
                            + content.getTitle()
            );
        }

        return savedContent;
    }

    // =========================
    // USER DELETE
    // =========================

    public void deleteContent(
            Long id,
            Long userId) {

        FitnessContent content =
                getContentByIdForUser(id, userId);

        String title = content.getTitle();

        contentRepository.deleteById(id);

        activityLogService.logActivity(
                userId,
                "Deleted fitness content: " + title
        );
    }

    // =========================
    // ADMIN DELETE ANY CONTENT
    // =========================

    public void adminDeleteContent(Long id) {

        FitnessContent content =
                getContentById(id);

        Long ownerId = null;

        if (content.getSubmittedBy() != null) {
            ownerId = content.getSubmittedBy().getId();
        }

        String title = content.getTitle();

        contentRepository.deleteById(id);

        if (ownerId != null) {

            activityLogService.logActivity(
                    ownerId,
                    "Fitness content deleted by admin: "
                            + title
            );
        }
    }
}