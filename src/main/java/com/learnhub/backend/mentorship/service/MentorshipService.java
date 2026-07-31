package com.learnhub.backend.mentorship.service;

import com.learnhub.backend.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.mentorship.dto.response.SessionResponse;
import java.util.List;

public interface MentorshipService {

    DoubtSessionResponse bookSession(DoubtSessionRequest request);

    List<DoubtSessionResponse> getSessionsForLearner(Long learnerId);

    List<DoubtSessionResponse> getSessionsForCreator(Long creatorId);

    DoubtSessionResponse confirmPayment(Long sessionId, String transactionId);

    List<SessionResponse> getLearnerSessions(Long learnerId);
}
