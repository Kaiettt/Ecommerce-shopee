package com.ak.ecommerce_vender.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ak.ecommerce_vender.domain.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import jakarta.transaction.Transactional;
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>, JpaSpecificationExecutor<ConfirmationToken> {
    @Query("SELECT c FROM ConfirmationToken c WHERE c.token = :token AND c.user.email = :email")
    Optional<ConfirmationToken> findByTokenAndUserEmail(@Param("token") long token, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE confirmation_token ct SET ct.confirmed_at = ?2 WHERE ct.token = ?1 AND ct.user_id = (SELECT u.id FROM users u WHERE u.email = ?3)",
        nativeQuery = true
    )
    void updateConfirmedAt(long token, LocalDateTime confirmedAt, String email);
}
