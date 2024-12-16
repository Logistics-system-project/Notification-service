package com.spring.dozen.notification.domain.repository;

import com.spring.dozen.notification.domain.entity.AI;

import java.util.Optional;

public interface AIRepository {
    Optional<AI> findById(Long id);

    AI save(AI ai);
}
