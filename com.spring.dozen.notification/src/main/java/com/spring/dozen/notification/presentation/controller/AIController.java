package com.spring.dozen.notification.presentation.controller;

import com.spring.dozen.notification.application.aspect.RequireRole;
import com.spring.dozen.notification.application.dto.ai.AiListResponse;
import com.spring.dozen.notification.application.dto.ai.AiResponse;
import com.spring.dozen.notification.application.service.AIService;
import com.spring.dozen.notification.presentation.dto.ApiResponse;
import com.spring.dozen.notification.presentation.dto.PageResponse;
import com.spring.dozen.notification.presentation.dto.ai.AICreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
public class AIController {

    private final AIService aiService;

    @RequireRole({"MASTER", "HUB_MANAGER"})
    @PostMapping("/dispatch")
    public ApiResponse<AiResponse> dispatch(@RequestBody AICreateRequest aicreateRequest) {
        AiResponse response = aiService.create(aicreateRequest.toAICreate());
        return ApiResponse.success(response);
    }

    @RequireRole({"MASTER", "HUB_MANAGER"})
    @GetMapping("/dispatch")
    public PageResponse<AiListResponse> search(
            @RequestParam(value = "orderId", required = false) UUID orderId,
            @RequestParam(value = "departureHubId", required = false) UUID departureHubId,
            @RequestParam(value = "arrivalHubId", required = false) UUID arrivalHubId,
            Pageable pageable
    ) {
        Page<AiListResponse> responsePage = aiService.searchPage(orderId, departureHubId, arrivalHubId, pageable);
        return PageResponse.of(responsePage);
    }
}
