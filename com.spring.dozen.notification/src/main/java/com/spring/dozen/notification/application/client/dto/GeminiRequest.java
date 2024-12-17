package com.spring.dozen.notification.application.client.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record GeminiRequest(List<Content> contents) {
    public GeminiRequest(String query, String start, String destination, String deadline) {
        this(new ArrayList<>(Collections.singletonList(
                new Content(Collections.singletonList(
                        new Part(String.format("%s 시작: %s, 목적지: %s, 마감시간: %s",
                                query, start, destination, deadline))
                ))
        )));
    }

    public record Content(List<Part> parts) {}

    public record Part(String text) {}
}
