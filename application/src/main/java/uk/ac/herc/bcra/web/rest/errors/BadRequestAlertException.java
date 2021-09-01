package uk.ac.herc.bcra.web.rest.errors;

import org.zalando.problem.Status;
import uk.ac.herc.bcra.exception.HRYWException;

import java.util.HashMap;
import java.util.Map;

public class BadRequestAlertException extends HRYWException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, Status.BAD_REQUEST, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
