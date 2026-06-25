package com.arpan.url_shortener.ratelimiter;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SIZE_MS = 60_000;

    private final ConcurrentHashMap<String, RateLimitInfo> requests =
            new ConcurrentHashMap<>();

    public boolean isAllowed(String ipAddress) {

        long currentTime = System.currentTimeMillis();

        RateLimitInfo info =
                requests.get(ipAddress);

        if (info == null) {

            requests.put(
                    ipAddress,
                    new RateLimitInfo(1, currentTime)
            );

            return true;
        }

        if (currentTime - info.getWindowStartTime()
                > WINDOW_SIZE_MS) {

            info.setRequestCount(1);
            info.setWindowStartTime(currentTime);

            return true;
        }

        if (info.getRequestCount() >= MAX_REQUESTS) {
            return false;
        }

        info.setRequestCount(
                info.getRequestCount() + 1
        );

        return true;
    }
}