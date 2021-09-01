package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class EmailNotFoundException extends HRYWException {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException() {
        super("Email address not registered", Status.BAD_REQUEST);
    }
}
