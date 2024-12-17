package com.spring.dozen.notification.presentation.controller;

import com.spring.dozen.notification.application.aspect.RequireRole;
import com.spring.dozen.notification.application.dto.ai.AiListResponse;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreateResponse;
import com.spring.dozen.notification.application.dto.slack.SlackMessageResponse;
import com.spring.dozen.notification.application.service.SlackMessageService;
import com.spring.dozen.notification.presentation.dto.ApiResponse;
import com.spring.dozen.notification.presentation.dto.PageResponse;
import com.spring.dozen.notification.presentation.dto.slack.SlackMessageCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/slack-messages")
@RestController
public class SlackMessageController {
    private final SlackMessageService slackMessageService;

    @RequireRole({"MASTER", "HUB_MANAGER", "HUB_DELIVERY_STAFF", "COMPANY_DELIVERY_STAFF"})
    @PostMapping
    public ApiResponse<SlackMessageCreateResponse> send(@RequestBody SlackMessageCreateRequest request) {
        return ApiResponse.process(slackMessageService.send(request.toServiceDto()));
    }

    @RequireRole("MASTER")
    @GetMapping("/{slackMessageId}")
    public ApiResponse<SlackMessageResponse> findOne(@PathVariable("slackMessageId") UUID slackMessageId) {
        return ApiResponse.success(slackMessageService.findOne(slackMessageId));
    }

    @RequireRole("MASTER")
    @GetMapping()
    public PageResponse<SlackMessageResponse> searchPage(
            @RequestParam(value = "senderUserId", required = false) Long senderUserId,
            @RequestParam(value = "receiverUserId", required = false) Long receiverUserId,
            Pageable pageable){
        Page<SlackMessageResponse> responsePage = slackMessageService.searchPage(senderUserId, receiverUserId, pageable);
        return PageResponse.of(responsePage);
    }
}
