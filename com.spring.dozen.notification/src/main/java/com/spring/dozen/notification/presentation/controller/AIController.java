package com.spring.dozen.notification.presentation.controller;

import com.spring.dozen.notification.application.dto.ai.AiResponse;
import com.spring.dozen.notification.application.service.AIService;
import com.spring.dozen.notification.presentation.dto.ApiResponse;
import com.spring.dozen.notification.presentation.dto.ai.AICreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
public class AIController {

    private final AIService aiService;

    @PostMapping("/dispatch")
    public ApiResponse<AiResponse> dispatch(@RequestBody AICreateRequest aicreateRequest) {
        AiResponse response = aiService.create(aicreateRequest.toAICreate());
        return ApiResponse.success(response);
    }
}
