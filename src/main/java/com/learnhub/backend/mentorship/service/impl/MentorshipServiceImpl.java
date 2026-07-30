package com.learnhub.backend.mentorship.service.impl;

import com.learnhub.backend.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.mentorship.entity.DoubtSession;
import com.learnhub.backend.mentorship.repository.DoubtSessionRepository;
import com.learnhub.backend.mentorship.service.MentorshipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MentorshipServiceImpl — Implementation class for Mentorship Service.
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class MentorshipServiceImpl implements MentorshipService {

    private final DoubtSessionRepository repository;

    // Explicit Constructor Injection
    public MentorshipServiceImpl(DoubtSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SessionResponse> getLearnerSessions(Long learnerId) {
        return repository.findByLearnerId(learnerId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private SessionResponse map(DoubtSession session) {
        String bookingStatus = session.getBookingStatus() != null ? session.getBookingStatus().name() : "CONFIRMED";
        String paymentStatus = session.getPaymentStatus() != null ? session.getPaymentStatus().name() : "PAID";
        String creatorName = session.getCreator() != null ? session.getCreator().getName() : "LearnHub Mentor";

        return new SessionResponse(
                session.getId(),
                session.getTopic(),
                session.getScheduledAt(),
                session.getDurationMinutes(),
                session.getSessionPrice(),
                bookingStatus,
                paymentStatus,
                creatorName,
                session.getJitsiRoomName()
        );
    }
}