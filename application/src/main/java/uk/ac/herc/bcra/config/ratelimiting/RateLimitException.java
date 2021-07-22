package uk.ac.herc.bcra.config.ratelimiting;

public class RateLimitException extends Exception {
    public RateLimitException(String message) {
        super(message);
    }
}