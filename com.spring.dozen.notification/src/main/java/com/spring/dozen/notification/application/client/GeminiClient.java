package com.spring.dozen.notification.application.client;

import com.spring.dozen.notification.application.client.dto.GeminiResponse;
import com.spring.dozen.notification.application.client.dto.GeminiRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface GeminiClient {

    GeminiResponse getCompletion(@RequestBody GeminiRequest request);
}
