package com.learnhub.backend.modules.user.repository;

import com.learnhub.backend.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
           "(cast(:search as string) IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', cast(:search as string), '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', cast(:search as string), '%')))")
    List<User> searchUsers(@Param("search") String search);

    List<User> findByRole(String role);

    long countByRole(String role);
}
