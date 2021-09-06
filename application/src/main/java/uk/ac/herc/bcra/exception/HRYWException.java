package uk.ac.herc.bcra.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.Collections;
import java.util.Map;

public class HRYWException extends AbstractThrowableProblem {
    public HRYWException(String message) {
        this(message, Status.INTERNAL_SERVER_ERROR);
    }

    public HRYWException(String message, Status status) {
        this(message, status, Collections.singletonMap("message", message));
    }

    public HRYWException(String message, Status status, Map<String, Object> parameters) {
        super(null, message, status, null, null,null, parameters);
    }
}
