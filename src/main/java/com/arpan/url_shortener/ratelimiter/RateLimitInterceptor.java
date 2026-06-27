package com.arpan.url_shortener.ratelimiter;

import com.arpan.url_shortener.service.RedisRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisRateLimiterService redisRateLimiterService;
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ip = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }

        if (!redisRateLimiterService.allowRequest(ip)) {
            response.sendError(
                    HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Rate limit exceeded"
            );
            return false;
        }

        return true;
    }
}
