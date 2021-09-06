package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

public class DuplicateStudyIdException extends HRYWException {
    public DuplicateStudyIdException(String message) {
        super(message, Status.BAD_REQUEST);
    }
}
