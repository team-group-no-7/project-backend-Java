package com.learnhub.backend.mentorship.service;

import com.learnhub.backend.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.mentorship.entity.DoubtSession;
import com.learnhub.backend.mentorship.repository.DoubtSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MentorshipServiceImpl implements MentorshipService {

    @Autowired
    private DoubtSessionRepository doubtSessionRepository;

    @Override
    public DoubtSessionResponse bookSession(DoubtSessionRequest request) {
        DoubtSession session = new DoubtSession();
        session.setLearnerId(request.getLearnerId());
        session.setCreatorId(request.getCreatorId());
        session.setTopic(request.getTopic());
        session.setScheduledAt(request.getScheduledAt());
        session.setDurationMinutes(request.getDurationMinutes());
        session.setSessionPrice(request.getSessionPrice());
        
        // Default pending states
        session.setBookingStatus("PENDING");
        session.setPaymentStatus("UNPAID");

        // Generate Jitsi Room Hash
        String cleanTopic = request.getTopic().replaceAll("[^a-zA-Z0-9-]", "").toLowerCase();
        if (cleanTopic.isEmpty()) cleanTopic = "doubt";
        session.setJitsiRoomName("learnhub-" + cleanTopic + "-" + UUID.randomUUID().toString().substring(0, 8));

        DoubtSession savedSession = doubtSessionRepository.save(session);
        return mapToResponse(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoubtSessionResponse> getSessionsForLearner(Long learnerId) {
        List<DoubtSession> sessions = doubtSessionRepository.findByLearnerId(learnerId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoubtSessionResponse> getSessionsForCreator(Long creatorId) {
        List<DoubtSession> sessions = doubtSessionRepository.findByCreatorId(creatorId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public DoubtSessionResponse confirmPayment(Long sessionId, String transactionId) {
        DoubtSession session = doubtSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Doubt session not found with id: " + sessionId));
        
        session.setPaymentStatus("PAID");
        session.setBookingStatus("CONFIRMED");
        session.setTransactionId(transactionId);

        DoubtSession savedSession = doubtSessionRepository.save(session);
        return mapToResponse(savedSession);
    }

    // Helper mapper to generate Jitsi Link dynamically
    private DoubtSessionResponse mapToResponse(DoubtSession session) {
        DoubtSessionResponse response = new DoubtSessionResponse();
        response.setId(session.getId());
        response.setLearnerId(session.getLearnerId());
        response.setCreatorId(session.getCreatorId());
        response.setTopic(session.getTopic());
        response.setScheduledAt(session.getScheduledAt());
        response.setDurationMinutes(session.getDurationMinutes());
        response.setSessionPrice(session.getSessionPrice());
        response.setBookingStatus(session.getBookingStatus());
        response.setPaymentStatus(session.getPaymentStatus());
        response.setTransactionId(session.getTransactionId());
        response.setJitsiRoomName(session.getJitsiRoomName());
        
        // Compute active video link
        if (session.getJitsiRoomName() != null) {
            response.setJitsiMeetingLink("https://meet.jit.si/" + session.getJitsiRoomName());
        }
        
        return response;
    }
}
