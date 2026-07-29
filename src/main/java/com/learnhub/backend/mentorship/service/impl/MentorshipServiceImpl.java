package com.learnhub.backend.mentorship.service.impl;

import com.learnhub.backend.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.mentorship.entity.DoubtSession;
import com.learnhub.backend.mentorship.repository.DoubtSessionRepository;
import com.learnhub.backend.mentorship.service.MentorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService {

    private final DoubtSessionRepository repository;

    @Override
    public List<SessionResponse> getLearnerSessions(Long learnerId) {

        return repository.findByLearnerId(learnerId)
                .stream()
                .map(this::map)
                .toList();
    }

    private SessionResponse map(DoubtSession session){

        return SessionResponse.builder()
                .id(session.getId())
                .topic(session.getTopic())
                .scheduledAt(session.getScheduledAt())
                .durationMinutes(session.getDurationMinutes())
                .sessionPrice(session.getSessionPrice())
                .bookingStatus(session.getBookingStatus().name())
                .paymentStatus(session.getPaymentStatus().name())
                .creatorName(session.getCreator().getName())
                .jitsiRoomName(session.getJitsiRoomName())
                .build();
    }

}