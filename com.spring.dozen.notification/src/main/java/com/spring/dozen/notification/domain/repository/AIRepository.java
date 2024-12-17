package com.spring.dozen.notification.domain.repository;

import com.spring.dozen.notification.domain.entity.AI;

import java.util.Optional;
import java.util.UUID;

public interface AIRepository {
    Optional<AI> findById(UUID id);

    AI save(AI ai);
}
