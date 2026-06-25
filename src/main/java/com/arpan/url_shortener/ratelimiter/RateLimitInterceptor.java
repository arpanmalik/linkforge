package com.arpan.url_shortener.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ipAddress = request.getRemoteAddr();

        if (!rateLimiterService.isAllowed(ipAddress)) {

            response.setStatus(429);
            response.getWriter()
                    .write("Rate limit exceeded. Try again later.");

            return false;
        }

        return true;
    }
}
