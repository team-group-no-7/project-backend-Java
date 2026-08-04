package com.learnhub.backend.modules.discussion.service.impl;

import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.discussion.dto.response.QAReplyResponse;
import com.learnhub.backend.modules.discussion.dto.response.QAThreadResponse;
import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;
import com.learnhub.backend.modules.discussion.repository.QAThreadRepository;
import com.learnhub.backend.modules.discussion.service.DiscussionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DiscussionServiceImpl — Implementation class for Content Q&A Discussion Forum using QAThreadResponse DTOs.
 */
@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final QAThreadRepository qaThreadRepository;

    public DiscussionServiceImpl(QAThreadRepository qaThreadRepository) {
        this.qaThreadRepository = qaThreadRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QAThreadResponse> getThreadsForContent(Long contentId) {
        List<QAThread> threads = qaThreadRepository.findByContentIdOrderByIdDesc(contentId);
        return threads.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QAThreadResponse createQuestion(QAThread thread) {
        if (thread.getQuestion() == null || thread.getQuestion().trim().isEmpty()) {
            throw new BadRequestException("Question body cannot be empty");
        }
        QAThread saved = qaThreadRepository.save(thread);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public QAThreadResponse addReply(Long threadId, QAReply reply) {
        QAThread thread = qaThreadRepository.findById(threadId)
                .orElseThrow(() -> new ResourceNotFoundException("Thread not found with id: " + threadId));

        thread.getReplies().add(reply);
        if ("CREATOR".equalsIgnoreCase(reply.getRole())) {
            reply.setIsVerifiedAnswer(true);
            thread.setIsResolved(true);
        }

        QAThread updated = qaThreadRepository.save(thread);
        return mapToResponse(updated);
    }

    private QAThreadResponse mapToResponse(QAThread thread) {
        List<QAReplyResponse> replyResponses = thread.getReplies() != null
                ? thread.getReplies().stream()
                .map(r -> new QAReplyResponse(
                        r.getId(),
                        thread.getId(),
                        r.getAuthorName(),
                        r.getRole(),
                        r.getReply(),
                        r.getUpvotes(),
                        r.getIsVerifiedAnswer(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList())
                : List.of();

        return new QAThreadResponse(
                thread.getId(),
                thread.getContentId(),
                thread.getAuthorName(),
                thread.getRole(),
                thread.getQuestion(),
                thread.getUpvotes(),
                thread.getIsResolved(),
                thread.getCreatedAt(),
                replyResponses
        );
    }
}
