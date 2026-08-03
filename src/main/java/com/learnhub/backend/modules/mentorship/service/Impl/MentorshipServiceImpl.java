package com.learnhub.backend.modules.mentorship.service.Impl;

import com.learnhub.backend.modules.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.modules.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.modules.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.modules.mentorship.entity.DoubtSession;
import com.learnhub.backend.modules.mentorship.enums.BookingStatus;
import com.learnhub.backend.modules.mentorship.enums.PaymentStatus;
import com.learnhub.backend.modules.mentorship.repository.DoubtSessionRepository;
import com.learnhub.backend.modules.mentorship.service.MentorshipService;
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
        
        // Set approved booking and paid status for completed transactions
        session.setBookingStatus(BookingStatus.APPROVED);
        session.setPaymentStatus(PaymentStatus.PAID);

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
        
        session.setPaymentStatus(PaymentStatus.PAID);
        session.setBookingStatus(BookingStatus.APPROVED);
        session.setTransactionId(transactionId);

        DoubtSession savedSession = doubtSessionRepository.save(session);
        return mapToResponse(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> getLearnerSessions(Long learnerId) {
        return doubtSessionRepository.findByLearnerId(learnerId)
                .stream()
                .map(this::mapToSessionResponse)
                .collect(Collectors.toList());
    }

    // Helper mapper to generate DoubtSessionResponse
    private DoubtSessionResponse mapToResponse(DoubtSession session) {
        DoubtSessionResponse response = new DoubtSessionResponse();
        response.setId(session.getId());
        response.setLearnerId(session.getLearnerId());
        response.setCreatorId(session.getCreatorId());
        response.setTopic(session.getTopic());
        response.setScheduledAt(session.getScheduledAt());
        response.setDurationMinutes(session.getDurationMinutes());
        response.setSessionPrice(session.getSessionPrice());
        
        if (session.getBookingStatus() != null) {
            response.setBookingStatus(session.getBookingStatus().name());
        }
        if (session.getPaymentStatus() != null) {
            response.setPaymentStatus(session.getPaymentStatus().name());
        }
        
        response.setTransactionId(session.getTransactionId());
        response.setJitsiRoomName(session.getJitsiRoomName());
        
        if (session.getJitsiRoomName() != null) {
            response.setJitsiMeetingLink("https://meet.jit.si/" + session.getJitsiRoomName());
        }

        if (session.getLearner() != null) {
            response.setLearnerName(session.getLearner().getName());
        }
        if (session.getCreator() != null) {
            response.setCreatorName(session.getCreator().getName());
        }
        
        return response;
    }

    // Helper mapper to generate Riya's SessionResponse
    private SessionResponse mapToSessionResponse(DoubtSession session) {
        String bookingStatus = session.getBookingStatus() != null ? session.getBookingStatus().name() : "PENDING";
        String paymentStatus = session.getPaymentStatus() != null ? session.getPaymentStatus().name() : "PENDING";
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
