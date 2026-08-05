package com.learnhub.backend.modules.mentorship.service.impl;

import com.learnhub.backend.modules.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.modules.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.modules.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.modules.mentorship.entity.DoubtSession;
import com.learnhub.backend.modules.mentorship.enums.BookingStatus;
import com.learnhub.backend.modules.mentorship.enums.PaymentStatus;
import com.learnhub.backend.modules.mentorship.repository.DoubtSessionRepository;
import com.learnhub.backend.modules.mentorship.service.MentorshipService;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * MentorshipServiceImpl — Implementation class for Learner Mentorship Service with SLF4J logging.
 */
@Service
@Transactional
public class MentorshipServiceImpl implements MentorshipService {

    private static final Logger log = LoggerFactory.getLogger(MentorshipServiceImpl.class);

    private final DoubtSessionRepository doubtSessionRepository;
    private final UserRepository userRepository;

    public MentorshipServiceImpl(DoubtSessionRepository doubtSessionRepository, UserRepository userRepository) {
        this.doubtSessionRepository = doubtSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DoubtSessionResponse bookSession(DoubtSessionRequest request) {
        log.info("Booking mentorship doubt session for topic: '{}'", request.getTopic());
        if (request.getLearnerId() != null) {
            SecurityUtils.validateOwnershipByUserId(request.getLearnerId(), userRepository);
        }

        // Creator self-booking prevention check
        if (request.getLearnerId() != null && request.getCreatorId() != null && request.getLearnerId().equals(request.getCreatorId())) {
            log.warn("Mentorship session booking failed. Self-booking attempted by user ID: {}", request.getLearnerId());
            throw new com.learnhub.backend.common.exception.BadRequestException("Creators cannot book mentorship sessions with themselves.");
        }

        // Checkpoint 3.3: Business Constraint Validation (Past-date prevention & non-negative price)
        if (request.getScheduledAt() != null && request.getScheduledAt().isBefore(java.time.LocalDateTime.now().minusMinutes(5))) {
            log.warn("Mentorship session booking failed. Past date requested: {}", request.getScheduledAt());
            throw new com.learnhub.backend.common.exception.BadRequestException("Mentorship session scheduled time must be in the future.");
        }

        if (request.getSessionPrice() != null && request.getSessionPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new com.learnhub.backend.common.exception.BadRequestException("Session price cannot be negative.");
        }

        DoubtSession session = new DoubtSession();
        session.setLearnerId(request.getLearnerId());
        session.setCreatorId(request.getCreatorId());
        session.setTopic(request.getTopic());
        session.setScheduledAt(request.getScheduledAt());
        session.setDurationMinutes(request.getDurationMinutes());
        session.setSessionPrice(request.getSessionPrice());
        
        session.setBookingStatus(BookingStatus.APPROVED);
        session.setPaymentStatus(PaymentStatus.PAID);

        String cleanTopic = request.getTopic().replaceAll("[^a-zA-Z0-9-]", "").toLowerCase();
        if (cleanTopic.isEmpty()) cleanTopic = "doubt";
        session.setJitsiRoomName("learnhub-" + cleanTopic + "-" + UUID.randomUUID().toString().substring(0, 8));

        DoubtSession savedSession = doubtSessionRepository.save(session);
        log.info("Successfully booked mentorship session ID: {} with Jitsi room: {}", savedSession.getId(), savedSession.getJitsiRoomName());
        return mapToResponse(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoubtSessionResponse> getSessionsForLearner(Long learnerId) {
        log.info("Fetching doubt sessions for learner ID: {}", learnerId);
        SecurityUtils.validateOwnershipByUserId(learnerId, userRepository);
        List<DoubtSession> sessions = doubtSessionRepository.findByLearnerId(learnerId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoubtSessionResponse> getSessionsForCreator(Long creatorId) {
        log.info("Fetching doubt sessions for creator ID: {}", creatorId);
        SecurityUtils.validateOwnershipByUserId(creatorId, userRepository);
        List<DoubtSession> sessions = doubtSessionRepository.findByCreatorId(creatorId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public DoubtSessionResponse confirmPayment(Long sessionId, String transactionId) {
        log.info("Confirming payment for doubt session ID: {}", sessionId);
        DoubtSession session = doubtSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Doubt session not found with id: " + sessionId));
        
        session.setPaymentStatus(PaymentStatus.PAID);
        session.setBookingStatus(BookingStatus.APPROVED);
        session.setTransactionId(transactionId);

        DoubtSession savedSession = doubtSessionRepository.save(session);
        log.info("Successfully confirmed payment for session ID: {}", savedSession.getId());
        return mapToResponse(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public DoubtSessionResponse getSessionDetail(Long sessionId) {
        log.info("Fetching mentorship session detail for session ID: {}", sessionId);
        DoubtSession session = doubtSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Doubt session not found with id: " + sessionId));

        if (!SecurityUtils.isAdmin()) {
            String currentEmail = SecurityUtils.getCurrentUserEmail();
            User currentUser = userRepository.findByEmail(currentEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));

            boolean isLearnerParticipant = session.getLearnerId() != null && session.getLearnerId().equals(currentUser.getId());
            boolean isCreatorParticipant = session.getCreatorId() != null && session.getCreatorId().equals(currentUser.getId());

            if (!isLearnerParticipant && !isCreatorParticipant) {
                throw new org.springframework.security.access.AccessDeniedException("You are not a participant in this mentorship session.");
            }
        }

        return mapToResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> getLearnerSessions(Long learnerId) {
        log.info("Fetching learner sessions list for learner ID: {}", learnerId);
        return doubtSessionRepository.findByLearnerId(learnerId)
                .stream()
                .map(this::mapToSessionResponse)
                .collect(Collectors.toList());
    }

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
