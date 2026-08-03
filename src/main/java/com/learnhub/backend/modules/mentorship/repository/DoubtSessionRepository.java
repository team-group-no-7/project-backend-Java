package com.learnhub.backend.modules.mentorship.repository;

import com.learnhub.backend.modules.mentorship.entity.DoubtSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoubtSessionRepository extends JpaRepository<DoubtSession, Long> {
    List<DoubtSession> findByLearnerId(Long learnerId);
    List<DoubtSession> findByCreatorId(Long creatorId);
}
