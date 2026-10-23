package com.bulk.kyro.api.filters;

import com.bulk.kyro.api.models.user.UserContext;
import com.bulk.kyro.api.utils.RateLimit;
import com.bulk.kyro.bll.exceptions.RateLimitException;
import com.bulk.kyro.bll.services.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimitService rateLimitService;

    @Before("@annotation(rateLimit)")
    public void enforceRateLimit(JoinPoint joinPoint, RateLimit rateLimit) throws RateLimitException {
        String identifier = extractIdentifier();
        String endpoint = extractEndpoint(joinPoint);

        log.debug("Rate limit check for {} on {}", identifier, endpoint);

        try {
            rateLimitService.checkRateLimit(
                    identifier,
                    endpoint,
                    rateLimit.maxRequests(),
                    rateLimit.windowSeconds()
            );
        } catch (RateLimitException e) {
            log.warn("Rate limit exceeded for {} on {}", identifier, endpoint);
            throw e;
        }
    }

    private String extractIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserContext userContext) {
                return "user_" + userContext.id();
            }
        }

        return "ip_" + getClientIp();
    }

    private String getClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String xForwardedFor = request.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                return xForwardedFor.split(",")[0].trim();
            }

            String remoteAddr = request.getHeader("X-Real-IP");
            if (remoteAddr != null && !remoteAddr.isEmpty()) {
                return remoteAddr;
            }

            return request.getRemoteAddr();
        }

        return "unknown";
    }

    private String extractEndpoint(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }
}