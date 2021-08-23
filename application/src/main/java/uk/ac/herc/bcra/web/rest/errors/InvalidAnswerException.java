package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;

public class InvalidAnswerException extends AbstractThrowableProblem {
    public InvalidAnswerException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message);
    }
}
