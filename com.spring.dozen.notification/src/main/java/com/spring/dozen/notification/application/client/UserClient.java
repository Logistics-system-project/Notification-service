package com.spring.dozen.notification.application.client;

import com.spring.dozen.notification.application.client.dto.UserResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserClient {
    UserResponse getUsersForSlack(@RequestParam("senderId") Long senderId,
                                  @RequestParam("receiverId") Long receiverId);

}
