package com.spring.dozen.notification.presentation.controller;

import com.spring.dozen.notification.application.aspect.RoleCheckAspect;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreateResponse;
import com.spring.dozen.notification.application.service.SlackMessageService;
import com.spring.dozen.notification.infra.config.aop.AopConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import({RoleCheckAspect.class, AopConfig.class})
@WebMvcTest(SlackMessageController.class)
class SlackMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SlackMessageService slackMessageService;

    @DisplayName("MASTER 권한인 경우 status 200")
    @Test
    void sendMessageWithMasterRole() throws Exception {
        SlackMessageCreateResponse mockResponse
                = new SlackMessageCreateResponse(
                UUID.randomUUID(),
                "테스트 메시지",
                1L,
                "senderSlackId",
                2L,
                "receiverSlackId",
                LocalDateTime.now()
                );
        given(slackMessageService.send(any())).willReturn(mockResponse);

        mockMvc.perform(post("/api/slack-messages")
                        .header("X-Role", "MASTER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test message\"}"))
                .andExpect(status().isOk());
    }

    @DisplayName("권한이 없다면 400 상태코드")
    @Test
    void sendMessageWithoutRole() throws Exception {
        mockMvc.perform(post("/api/slack-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Test message\"}"))
                .andExpect(status().isBadRequest());
    }
}