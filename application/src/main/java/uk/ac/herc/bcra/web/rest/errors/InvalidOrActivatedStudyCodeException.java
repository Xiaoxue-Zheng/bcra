package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class InvalidOrActivatedStudyCodeException extends HRYWException {

    private static final long serialVersionUID = 1L;

    public InvalidOrActivatedStudyCodeException() {
        super("Study code entered is either already in use, or invalid", Status.BAD_REQUEST);
    }
}
