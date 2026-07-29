package com.learnhub.backend.mentorship.service;

import com.learnhub.backend.mentorship.dto.response.SessionResponse;

import java.util.List;

public interface MentorshipService {

    List<SessionResponse> getLearnerSessions(Long learnerId);

}