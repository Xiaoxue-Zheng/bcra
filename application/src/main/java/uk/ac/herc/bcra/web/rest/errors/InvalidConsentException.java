package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidConsentException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public InvalidConsentException() {
        super(ErrorConstants.INVALID_CONSENT_RESPONSE, "One or more of the consent questions was rejected by user", Status.BAD_REQUEST);
    }
}
