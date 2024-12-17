package com.spring.dozen.notification.application.aspect;

import com.spring.dozen.notification.application.exception.NotificationErrorCode;
import com.spring.dozen.notification.application.exception.NotificationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final HttpServletRequest request;

    @Before("@annotation(requireRole)")
    public void checkRole(RequireRole requireRole) {
        // 헤더에서 role 정보 가져오기
        String role = request.getHeader("X-Role");
        if (role == null) {
            throw new NotificationException(NotificationErrorCode.MISSING_ROLE);
        }

        log.debug("Checking role {}", role);
        // 허용된 권한 체크
        List<String> allowedRoles = List.of(requireRole.value());
        if (!allowedRoles.contains(role)) {
            throw new NotificationException(NotificationErrorCode.FORBIDDEN_ACCESS);
        }
    }
}
