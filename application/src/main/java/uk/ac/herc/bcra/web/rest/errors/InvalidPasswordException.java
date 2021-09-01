package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class InvalidPasswordException extends HRYWException {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Incorrect password", Status.BAD_REQUEST);
    }
}
