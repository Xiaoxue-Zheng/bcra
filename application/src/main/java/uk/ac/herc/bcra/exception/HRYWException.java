package uk.ac.herc.bcra.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.Map;

public class HRYWException extends AbstractThrowableProblem {
    public HRYWException(String message, Status status) {
        super(null, message, status);
    }

    public HRYWException(String message, Status status, Map<String, Object> parameters) {
        super(null, message, status, null, null,null, parameters);
    }
}
