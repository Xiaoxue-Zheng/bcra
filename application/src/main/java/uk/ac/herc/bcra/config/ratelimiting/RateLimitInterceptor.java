package uk.ac.herc.bcra.config.ratelimiting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RateLimiterService limiter;
    
    private final int DEFAULT_RATE_LIMIT = 4; // 4 times per second
    private final int SECURE_ENDPOINT_RATE_LIMIT = 1; //1 time per second

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI().toLowerCase();

        //TODO: Add endpoints for NHS number checking and DoB checking here.
        if (uri.endsWith("/api/login/login") || 
            (uri.endsWith("/api/study-ids") && request.getMethod() == "POST")) {
            return limiter.isWithinLimit(request, SECURE_ENDPOINT_RATE_LIMIT);
        } else {
            return limiter.isWithinLimit(request, DEFAULT_RATE_LIMIT);
        }
    }
}