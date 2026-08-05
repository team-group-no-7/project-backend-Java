package com.learnhub.backend.modules.discussion.service.impl;

import com.learnhub.backend.common.exception.BadRequestException;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.discussion.dto.response.QAReplyResponse;
import com.learnhub.backend.modules.discussion.dto.response.QAThreadResponse;
import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;
import com.learnhub.backend.modules.discussion.repository.QAThreadRepository;
import com.learnhub.backend.modules.discussion.service.DiscussionService;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
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
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public DiscussionServiceImpl(QAThreadRepository qaThreadRepository,
                                 UserRepository userRepository,
                                 ContentRepository contentRepository) {
        this.qaThreadRepository = qaThreadRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
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

        String currentEmail = SecurityUtils.getCurrentUserEmail();
        Content content = contentRepository.findById(thread.getContentId()).orElse(null);

        boolean isCourseAuthor = content != null && content.getCreator() != null && currentEmail.equalsIgnoreCase(content.getCreator().getEmail());
        boolean isAdmin = SecurityUtils.isAdmin();

        thread.getReplies().add(reply);
        if (isCourseAuthor || isAdmin) {
            reply.setIsVerifiedAnswer(true);
            thread.setIsResolved(true);
        } else {
            reply.setIsVerifiedAnswer(false);
        }

        QAThread updated = qaThreadRepository.save(thread);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteThread(Long threadId) {
        QAThread thread = qaThreadRepository.findById(threadId)
                .orElseThrow(() -> new ResourceNotFoundException("Discussion thread not found with id: " + threadId));

        if (!SecurityUtils.isAdmin()) {
            String currentEmail = SecurityUtils.getCurrentUserEmail();
            User currentUser = userRepository.findByEmail(currentEmail).orElse(null);
            boolean isAuthorNameMatch = currentUser != null && currentUser.getName() != null && currentUser.getName().equalsIgnoreCase(thread.getAuthorName());
            boolean isAuthorEmailMatch = currentEmail.equalsIgnoreCase(thread.getAuthorName());

            if (!isAuthorNameMatch && !isAuthorEmailMatch) {
                throw new AccessDeniedException("You are not authorized to delete another user's discussion post.");
            }
        }

        qaThreadRepository.delete(thread);
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
