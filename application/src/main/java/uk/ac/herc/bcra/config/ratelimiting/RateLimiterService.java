package uk.ac.herc.bcra.config.ratelimiting;

import javax.servlet.http.HttpServletRequest;

public interface RateLimiterService {
    boolean isWithinLimit(HttpServletRequest request, int limit) throws RateLimitException;
    void clearIpAddressCache();
}
