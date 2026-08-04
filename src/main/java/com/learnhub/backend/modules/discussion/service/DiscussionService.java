package com.learnhub.backend.modules.discussion.service;

import com.learnhub.backend.modules.discussion.dto.response.QAThreadResponse;
import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;

import java.util.List;

/**
 * DiscussionService — Business logic interface for Content Q&A Discussion Forum using QAThreadResponse DTOs.
 */
public interface DiscussionService {

    /**
     * Fetch all discussion threads for a specific learning resource.
     */
    List<QAThreadResponse> getThreadsForContent(Long contentId);

    /**
     * Post a new Q&A question thread.
     */
    QAThreadResponse createQuestion(QAThread thread);

    /**
     * Post a reply to an existing discussion thread.
     */
    QAThreadResponse addReply(Long threadId, QAReply reply);
}
