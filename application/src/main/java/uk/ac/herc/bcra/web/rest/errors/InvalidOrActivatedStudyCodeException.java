package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidOrActivatedStudyCodeException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public InvalidOrActivatedStudyCodeException() {
        super(ErrorConstants.INVALID_OR_ACTIVED_STUDY_CODE, "Study code entered is either already in use, or invalid", Status.BAD_REQUEST);
    }
}
