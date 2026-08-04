package com.learnhub.backend.modules.discussion.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.discussion.dto.response.QAThreadResponse;
import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;
import com.learnhub.backend.modules.discussion.service.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * QAThreadController — REST Controller for Content Q&A Discussion Forum using QAThreadResponse DTOs.
 */
@RestController
@RequestMapping("/api/qa")
@PreAuthorize("isAuthenticated()")
public class QAThreadController {

    private final DiscussionService discussionService;

    public QAThreadController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/content/{contentId}")
    public ResponseEntity<ApiResponse<List<QAThreadResponse>>> getThreadsForContent(@PathVariable Long contentId) {
        List<QAThreadResponse> threads = discussionService.getThreadsForContent(contentId);
        return ResponseEntity.ok(ApiResponse.success("Q&A threads retrieved successfully", threads));
    }

    @PostMapping("/question")
    public ResponseEntity<ApiResponse<QAThreadResponse>> createQuestion(@RequestBody QAThread thread) {
        QAThreadResponse saved = discussionService.createQuestion(thread);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Question posted successfully", saved));
    }

    @PostMapping("/thread/{threadId}/reply")
    public ResponseEntity<ApiResponse<QAThreadResponse>> addReply(@PathVariable Long threadId, @RequestBody QAReply reply) {
        QAThreadResponse updated = discussionService.addReply(threadId, reply);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reply posted successfully", updated));
    }

    @DeleteMapping("/thread/{threadId}")
    public ResponseEntity<ApiResponse<String>> deleteThread(@PathVariable Long threadId) {
        discussionService.deleteThread(threadId);
        return ResponseEntity.ok(ApiResponse.success("Discussion post deleted successfully", "DELETED"));
    }
}
