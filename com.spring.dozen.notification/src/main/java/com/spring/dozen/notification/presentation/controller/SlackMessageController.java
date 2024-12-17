package com.spring.dozen.notification.presentation.controller;

import com.spring.dozen.notification.application.aspect.RequireRole;
import com.spring.dozen.notification.application.dto.slack.SlackMessageResponse;
import com.spring.dozen.notification.application.service.SlackMessageService;
import com.spring.dozen.notification.presentation.dto.ApiResponse;
import com.spring.dozen.notification.presentation.dto.slack.SlackMessageCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/slack-messages")
@RestController
public class SlackMessageController {
    private final SlackMessageService slackMessageService;

    @RequireRole({"MASTER", "HUB_MANAGER", "HUB_DELIVERY_STAFF", "COMPANY_DELIVERY_STAFF"})
    @PostMapping
    public ApiResponse<SlackMessageResponse> send(@RequestBody SlackMessageCreateRequest request) {
        return ApiResponse.process(slackMessageService.send(request.toServiceDto()));
    }
}
