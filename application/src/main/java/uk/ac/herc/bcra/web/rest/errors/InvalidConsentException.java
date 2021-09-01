package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class InvalidConsentException extends HRYWException {

    private static final long serialVersionUID = 1L;

    public InvalidConsentException() {
        super("One or more of the consent questions was rejected by user", Status.BAD_REQUEST);
    }
}
