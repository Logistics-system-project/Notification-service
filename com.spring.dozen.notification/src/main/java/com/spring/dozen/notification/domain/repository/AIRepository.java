package com.spring.dozen.notification.domain.repository;

import com.spring.dozen.notification.domain.entity.AI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AIRepository {
    Optional<AI> findById(UUID id);

    AI save(AI ai);

    Page<AI> searchWithPage(UUID orderId,
                            UUID departureHubId,
                            UUID arrivalHubId,
                            Pageable pageable);
}
