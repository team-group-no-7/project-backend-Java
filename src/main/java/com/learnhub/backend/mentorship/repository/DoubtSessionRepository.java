package com.learnhub.backend.mentorship.repository;

import com.learnhub.backend.mentorship.entity.DoubtSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoubtSessionRepository
        extends JpaRepository<DoubtSession, Long> {

    List<DoubtSession> findByLearnerId(Long learnerId);

}