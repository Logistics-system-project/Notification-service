package com.spring.dozen.notification.infra.repository.ai;

import com.spring.dozen.notification.domain.entity.AI;
import com.spring.dozen.notification.domain.repository.AIRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AIRepositoryImpl extends JpaRepository<AI, UUID>, AIRepository, AIRepositoryCustom {
}
