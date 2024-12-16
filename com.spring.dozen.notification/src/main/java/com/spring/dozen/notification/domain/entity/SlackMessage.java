package com.spring.dozen.notification.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_slack_message")
public class SlackMessage extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "slack_message_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String messageContent;

    @Column(name = "sender_user_id", nullable = false)
    private Long senderUserId;

    @Column(name = "receiver_user_id", nullable = false)
    private Long receiverUserId;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public static SlackMessage create(String messageContent, Long senderUserId, Long receiverUserId) {
        return SlackMessage.builder()
                .messageContent(messageContent)
                .senderUserId(senderUserId)
                .receiverUserId(receiverUserId)
                .sentAt(LocalDateTime.now())
                .build();
    }


}
