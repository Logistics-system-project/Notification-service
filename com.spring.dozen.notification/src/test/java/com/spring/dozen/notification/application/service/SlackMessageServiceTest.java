package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.client.UserClient;
import com.spring.dozen.notification.application.client.dto.UserResponse;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreate;
import com.spring.dozen.notification.application.dto.slack.SlackMessageResponse;
import com.spring.dozen.notification.application.exception.NotificationException;
import com.spring.dozen.notification.domain.repository.SlackMessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class SlackMessageServiceTest {

    @Autowired
    private SlackMessageService slackMessageService;

    @Autowired
    private SlackMessageRepository slackMessageRepository;

    @MockBean
    private UserClient userClient;

    @DisplayName("슬랙 메시지 생성 - 성공")
    @Test
    void send_WithValidRequest_ShouldCreateSlackMessage() {
        // given
        String messageContent = "test message";
        Long senderUserId = 1L;
        Long receiverUserId = 2L;
        String senderSlackId = "userSlack001";
        String receiverSlackId = "userSlack002";

        SlackMessageCreate createRequest = new SlackMessageCreate(messageContent, senderUserId, receiverUserId);

        // Mock UserClient response
        given(userClient.getUsersForSlack(senderUserId, receiverUserId))
                .willReturn(new UserResponse(senderUserId, senderSlackId, receiverUserId, receiverSlackId));

        // when
        SlackMessageResponse response = slackMessageService.send(createRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.messageContent()).isEqualTo(messageContent);
        assertThat(response.senderUserId()).isEqualTo(senderUserId);
        assertThat(response.receiverUserId()).isEqualTo(receiverUserId);
        assertThat(response.senderSlackId()).isEqualTo(senderSlackId);
        assertThat(response.receiverSlackId()).isEqualTo(receiverSlackId);
    }

    @DisplayName("슬랙 메시지 생성 - 수신자 정보가 없으면 예외 발생한다.")
    @Test
    void send_Without_Receiver_ShouldThrowNotificationException() {
        // given
        String messageContent = "test message";
        Long senderUserId = 1L;
        Long receiverUserId = 2L;
        String senderSlackId = "userSlack001";

        SlackMessageCreate createRequest = new SlackMessageCreate(messageContent, senderUserId, receiverUserId);

        // Mock UserClient response without receiver slack id
        given(userClient.getUsersForSlack(senderUserId, receiverUserId))
                .willReturn(new UserResponse(senderUserId, senderSlackId, receiverUserId, ""));

        // when & then
        assertThatThrownBy(() -> slackMessageService.send(createRequest))
                .isInstanceOf(NotificationException.class)
                .hasMessage("발신자 또는 수신자 정보가 존재하지 않습니다.");
    }

    @DisplayName("슬랙 메시지 생성 - 발신자 정보가 없으면 예외 발생한다.")
    @Test
    void send_Without_Sender_ShouldThrowNotificationException() {
        // given
        String messageContent = "test message";
        Long senderUserId = 1L;
        Long receiverUserId = 2L;
        String receiverSlackId = "userSlack002";

        SlackMessageCreate createRequest = new SlackMessageCreate(messageContent, senderUserId, receiverUserId);

        // Mock UserClient response without sender slack id
        given(userClient.getUsersForSlack(senderUserId, receiverUserId))
                .willReturn(new UserResponse(senderUserId, "", receiverUserId, receiverSlackId));

        // when & then
        assertThatThrownBy(() -> slackMessageService.send(createRequest))
                .isInstanceOf(NotificationException.class)
                .hasMessage("발신자 또는 수신자 정보가 존재하지 않습니다.");
    }

    @DisplayName("슬랙 메시지 생성 - 발신자, 수신자 정보가 모두 없으면 예외 발생한다.")
    @Test
    void send_Without_Both_Side_ShouldThrowNotificationException() {
        // given
        String messageContent = "test message";
        Long senderUserId = 1L;
        Long receiverUserId = 2L;

        SlackMessageCreate createRequest = new SlackMessageCreate(messageContent, senderUserId, receiverUserId);

        // Mock UserClient response with empty slack ids
        given(userClient.getUsersForSlack(senderUserId, receiverUserId))
                .willReturn(new UserResponse(senderUserId, "", receiverUserId, ""));

        // when & then
        assertThatThrownBy(() -> slackMessageService.send(createRequest))
                .isInstanceOf(NotificationException.class)
                .hasMessage("발신자 또는 수신자 정보가 존재하지 않습니다.");
    }
}
