package uk.ac.herc.bcra.config.ratelimiting;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class RateLimitException extends HRYWException {
    public RateLimitException(String message) {
        super(message, Status.TOO_MANY_REQUESTS);
    }
}
