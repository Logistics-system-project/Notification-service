package com.spring.dozen.notification.infra.repository.ai;

import com.spring.dozen.notification.domain.entity.AI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AIRepositoryCustom {

    Page<AI> searchWithPage(UUID orderId,
                            UUID departureHubId,
                            UUID arrivalHubId,
                            Pageable pageable);
}