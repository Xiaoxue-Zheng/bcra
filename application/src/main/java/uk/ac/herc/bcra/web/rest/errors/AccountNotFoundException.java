package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class AccountNotFoundException extends HRYWException {

    public AccountNotFoundException() {
        super("User not found", Status.NOT_FOUND);
    }

    public AccountNotFoundException(String message) {
        super(message, Status.NOT_FOUND);
    }
}
