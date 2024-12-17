package com.spring.dozen.notification.application.client.dto;

import java.util.List;

public record GeminiResponse(List<Candidate> candidates) {

    public String getDeadLine() {
        return  candidates().get(0).content().parts().get(0).text();
    }

    public record Candidate(
            Content content,
            String finishReason,
            int index,
            List<SafetyRating> safetyRatings
    ) {}

    public record Content(
            List<TextPart> parts,
            String role
    ) {}

    public record TextPart(String text) {}

    public record SafetyRating(
            String category,
            String probability
    ) {}

}
