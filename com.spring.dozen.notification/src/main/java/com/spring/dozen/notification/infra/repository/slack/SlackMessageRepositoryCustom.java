package com.spring.dozen.notification.infra.repository.slack;

import com.spring.dozen.notification.domain.entity.SlackMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SlackMessageRepositoryCustom {

    Page<SlackMessage> searchWithPage(Long senderUserId,
                                      Long receiverUserId,
                                      Pageable pageable);
}
