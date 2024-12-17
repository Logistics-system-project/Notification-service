package com.spring.dozen.notification.infra.repository.slack;

import com.spring.dozen.notification.domain.entity.SlackMessage;
import com.spring.dozen.notification.domain.repository.SlackMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackMessageRepositoryImpl
        extends JpaRepository<SlackMessage, UUID>, SlackMessageRepository{
}
