package com.spring.dozen.notification.infra.client;

import com.spring.dozen.notification.application.client.GeminiClient;
import com.spring.dozen.notification.application.client.dto.GeminiResponse;
import com.spring.dozen.notification.infra.config.feign.GeminiFeignConfig;
import com.spring.dozen.notification.application.client.dto.GeminiRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "geminiApi", url = "${gemini.api.url}", configuration = GeminiFeignConfig.class)
public interface GeminiClientImpl extends GeminiClient {
    @PostMapping
    GeminiResponse getCompletion(@RequestBody GeminiRequest request);
}
