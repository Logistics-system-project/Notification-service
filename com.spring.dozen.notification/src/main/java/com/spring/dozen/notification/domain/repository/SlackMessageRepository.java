package com.spring.dozen.notification.domain.repository;

import com.spring.dozen.notification.domain.entity.SlackMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlackMessageRepository {
    Optional<SlackMessage> findById(UUID id);

    SlackMessage save(SlackMessage slackMessage);

    Page<SlackMessage> searchWithPage(Long senderUserId,
                                      Long receiverUserId,
                                      Pageable pageable);
}
