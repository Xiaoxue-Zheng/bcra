package uk.ac.herc.bcra.config.ratelimiting;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import uk.ac.herc.bcra.domain.IPRestriction;
import uk.ac.herc.bcra.repository.IPRestrictionRepository;

import javax.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final static int MAX_RATE_LIMIT_BREAKS_BEFORE_BAN = 50;

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(RateLimiterServiceImpl.class);
    private final Long TIMEOUT = 200l;

    private IPRestrictionRepository ipRestrictionRepository;

    public RateLimiterServiceImpl(IPRestrictionRepository ipRestrictionRepository) {
        this.ipRestrictionRepository = ipRestrictionRepository;
    }

    public boolean isWithinLimit(HttpServletRequest request, int limit) throws RateLimitException {
        String ipAddress = getIpAddressFromRequest(request);

        if (!hasIpRecordedAndExceededRateLimitBreaks(ipAddress)) {
            RateLimiter limiter = limiters.computeIfAbsent(ipAddress, name -> RateLimiter.create(limit));
            boolean acquired = limiter.tryAcquire(TIMEOUT, TimeUnit.MICROSECONDS);

            if (acquired) {
                return true;
            } else {
                String message = String.format("API rate limit exceeded for %s. Current rate limit is %s per second", ipAddress, limit);
                log.warn("Error: %s\nRateLimiter: %s", message, limiter.toString());

                registerRateLimitBreak(ipAddress);
                throw new RateLimitException(message);
            }
        } else {
            String message = String.format("The address %s has been temporarily banned.", ipAddress);
            log.warn("Error: %s\nTemporarily banned IP", message);

            throw new RateLimitException(message);
        }
    }

    private String getIpAddressFromRequest(HttpServletRequest request) throws RateLimitException {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress == null) {
                throw new RateLimitException("Rate limit: Unable to detect IP address.");
            }
        }

        return ipAddress;
    }

    private boolean hasIpRecordedAndExceededRateLimitBreaks(String ipAddress) {
        Optional<IPRestriction> ipRestrictionOptional = ipRestrictionRepository.findOneByIpAddress(ipAddress);
        if (ipRestrictionOptional.isPresent()) {
            IPRestriction ipRestriction = ipRestrictionOptional.get();
            return ipRestriction.getTimesRateLimitBroken() >= MAX_RATE_LIMIT_BREAKS_BEFORE_BAN;
        }

        return false;
    }

    public void registerRateLimitBreak(String ipAddress) {
        IPRestriction ipRestriction = getOrCreateIpRestrictionIfNotExists(ipAddress);

        ipRestriction.setTimesRateLimitBroken(ipRestriction.getTimesRateLimitBroken() + 1);
        ipRestriction.setLastRateLimitBreak(Instant.now());

        if (ipRestriction.getTimesRateLimitBroken() >= MAX_RATE_LIMIT_BREAKS_BEFORE_BAN) {
            ipRestriction.setBanDateTime(Instant.now());
        }

        ipRestrictionRepository.save(ipRestriction);
    }

    private IPRestriction getOrCreateIpRestrictionIfNotExists(String ipAddress) {
        Optional<IPRestriction> ipRestrictionOptional = ipRestrictionRepository.findOneByIpAddress(ipAddress);
        if (ipRestrictionOptional.isPresent()) {
            return ipRestrictionOptional.get();
        } else {
            IPRestriction ipRestriction = new IPRestriction();
            ipRestriction.setIpAddress(ipAddress);
            ipRestriction.setTimesRateLimitBroken(0);
            return ipRestrictionRepository.save(ipRestriction);
        }
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @Override
    public void clearIpAddressCache() {
        Instant aDayAgo = Instant.now().minus(24, ChronoUnit.HOURS);
        List<IPRestriction> ipRestrictions = ipRestrictionRepository.findByLastRateLimitBreakBefore(aDayAgo);
        for (IPRestriction ipRestriction : ipRestrictions) {
            limiters.remove(ipRestriction.getIpAddress());
            ipRestrictionRepository.delete(ipRestriction);
        }
    }
}
