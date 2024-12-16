package com.spring.dozen.notification.domain.repository;

import com.spring.dozen.notification.domain.entity.SlackMessage;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlackMessageRepository {
    Optional<SlackMessage> findById(UUID id);

    SlackMessage save(SlackMessage slackMessage);
}
