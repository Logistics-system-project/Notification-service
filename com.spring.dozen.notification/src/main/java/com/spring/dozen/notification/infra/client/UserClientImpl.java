package com.spring.dozen.notification.infra.client;

import com.spring.dozen.notification.application.client.UserClient;
import com.spring.dozen.notification.application.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface UserClientImpl extends UserClient {

    @GetMapping("/api/users/slack-ids")
    UserResponse getUsersForSlack(@RequestParam("senderId") Long senderId,
                                  @RequestParam("receiverId") Long receiverId);
}
