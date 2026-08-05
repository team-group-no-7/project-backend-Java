package com.learnhub.backend.modules.discussion.repository;

import com.learnhub.backend.modules.discussion.entity.QAThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QAThreadRepository extends JpaRepository<QAThread, Long> {
    List<QAThread> findByContentIdOrderByIdDesc(Long contentId);
}
