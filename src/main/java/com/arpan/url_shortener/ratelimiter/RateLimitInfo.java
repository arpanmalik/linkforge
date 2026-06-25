package com.arpan.url_shortener.ratelimiter;

public class RateLimitInfo {

    private int requestCount;
    private long windowStartTime;

    public RateLimitInfo(int requestCount, long windowStartTime) {
        this.requestCount = requestCount;
        this.windowStartTime = windowStartTime;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public long getWindowStartTime() {
        return windowStartTime;
    }

    public void setWindowStartTime(long windowStartTime) {
        this.windowStartTime = windowStartTime;
    }
}