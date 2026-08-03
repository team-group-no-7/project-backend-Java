package com.learnhub.backend.modules.discussion.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;
import com.learnhub.backend.modules.discussion.repository.QAThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/qa")
public class QAThreadController {

    @Autowired
    private QAThreadRepository qaThreadRepository;

    @GetMapping("/content/{contentId}")
    public ResponseEntity<ApiResponse<List<QAThread>>> getThreadsForContent(@PathVariable Long contentId) {
        List<QAThread> threads = qaThreadRepository.findByContentIdOrderByIdDesc(contentId);
        return ResponseEntity.ok(ApiResponse.success("Q&A threads retrieved successfully", threads));
    }

    @PostMapping("/question")
    public ResponseEntity<ApiResponse<QAThread>> createQuestion(@RequestBody QAThread thread) {
        if (thread.getQuestion() == null || thread.getQuestion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Question body cannot be empty"));
        }
        QAThread saved = qaThreadRepository.save(thread);
        return ResponseEntity.ok(ApiResponse.success("Question posted successfully", saved));
    }

    @PostMapping("/thread/{threadId}/reply")
    public ResponseEntity<ApiResponse<QAThread>> addReply(@PathVariable Long threadId, @RequestBody QAReply reply) {
        QAThread thread = qaThreadRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found with id: " + threadId));
        thread.getReplies().add(reply);
        if ("CREATOR".equalsIgnoreCase(reply.getRole())) {
            reply.setIsVerifiedAnswer(true);
            thread.setIsResolved(true);
        }
        QAThread updated = qaThreadRepository.save(thread);
        return ResponseEntity.ok(ApiResponse.success("Reply posted successfully", updated));
    }
}
